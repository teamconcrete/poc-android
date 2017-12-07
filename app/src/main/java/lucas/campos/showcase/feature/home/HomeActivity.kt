package lucas.campos.showcase.feature.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import kotlinx.android.synthetic.main.activity_home.*
import lucas.campos.showcase.R
import lucas.campos.showcase.data.model.Product


class HomeActivity : AppCompatActivity(), KodeinInjected {

    override val injector = KodeinInjector()

    private var mPaymentsClient: PaymentsClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPaymentsClient = Wallet.getPaymentsClient(
                this,
                Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build())

//        inject(appKodein())
//        val person: Person by instance()

        recycler_view.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?

        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.behavior.observe(this, Observer {
            when (it) {
                is HomeShowLoading -> { view_flipper.displayedChild = 1 }
                is HomeHideLoading -> { view_flipper.displayedChild = 0 }
                is HomeListData -> {
                    recycler_view.adapter = HomeAdapter(it.products, {
                        val dataRequest = createPaymentDataRequest()

                        dataRequest?.let {
                            if (mPaymentsClient != null) {
                                AutoResolveHelper.resolveTask(mPaymentsClient!!.loadPaymentData(dataRequest), this, 1)
                            } else {
                                toast("erro no google client")
                            }
                        }

                    })
                }
                is HomeError -> { toast("Deu erro") }
            }
        })

        viewModel.fetchData()
    }

    private fun isReadyToPay() {
        val request = IsReadyToPayRequest.newBuilder()
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .build()

        val task = mPaymentsClient?.isReadyToPay(request)

        task?.addOnCompleteListener({
            try {
                val result = it.getResult(ApiException::class.java)

                when (result) {
                    true -> { toast("It's works") }
                    false -> { toast("something wrong :/") }
                }
            } catch (exception: ApiException) {
                toast("~~exception()~~")
            }
        })
    }

    private fun createPaymentDataRequest() : PaymentDataRequest? {

        val cardRequirements = PaymentDataRequest.newBuilder()
                .setTransactionInfo(
                        TransactionInfo.newBuilder()
                                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                .setTotalPrice("0.10")
                                .setCurrencyCode("USD")
                                .build())
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .setCardRequirements(
                        CardRequirements.newBuilder()
                                .addAllowedCardNetworks(
                                        arrayListOf(
                                                WalletConstants.CARD_NETWORK_AMEX,
                                                WalletConstants.CARD_NETWORK_DISCOVER,
                                                WalletConstants.CARD_NETWORK_VISA,
                                                WalletConstants.CARD_NETWORK_MASTERCARD
                                        )
                                )
                                .build()
                )

        val paymentToken = PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(
                        WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                .addParameter("gateway", "test")
                .addParameter("gatewayMerchantId", "testId")
                .build()

        cardRequirements.setPaymentMethodTokenizationParameters(paymentToken)

        return cardRequirements.build()
    }

    fun AppCompatActivity.toast(message: String) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

    private fun fetchProducts() =
            listOf(
                    Product(
                            "Nome do produto",
                            "Câmeras Compactas",
                            ""
                    ),
                    Product(
                            "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                            "Binóculos e Telescópios",
                            ""
                    ),
                    Product(
                            "Camera Digital Sony Cyber-Shot DSC-HX400V",
                            "Câmeras Compactas",
                            ""
                    ),
                    Product(
                            "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                            "Binóculos e Telescópios",
                            ""
                    )
            )
}