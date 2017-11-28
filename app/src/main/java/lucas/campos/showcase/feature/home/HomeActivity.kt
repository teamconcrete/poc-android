package lucas.campos.showcase.feature.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import kotlinx.android.synthetic.main.activity_home.*
import lucas.campos.showcase.Person
import lucas.campos.showcase.R
import lucas.campos.showcase.data.model.Product


class HomeActivity : AppCompatActivity(), KodeinInjected {

    override val injector = KodeinInjector()

    private var mPaymentsClient: PaymentsClient? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPaymentsClient = Wallet.getPaymentsClient(
                this,
                Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build())

        inject(appKodein())

        val person: Person by instance()

        val products = listOf(
                Product(
                        person.name,
                        "Câmeras Compactas",
                        1999.90
                ),
                Product(
                        "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                        "Binóculos e Telescópios",
                        169.90
                ),
                Product(
                        "Camera Digital Sony Cyber-Shot DSC-HX400V",
                        "Câmeras Compactas",
                        1999.90
                ),
                Product(
                            "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                        "Binóculos e Telescópios",
                        169.90
                )
        )

        recycler_view.adapter = HomeAdapter(products, {
            val dataRequest = createPaymentDataRequest()

            dataRequest?.let {
                if (mPaymentsClient != null) {
                    AutoResolveHelper.resolveTask(mPaymentsClient!!.loadPaymentData(dataRequest), this, 1)
                } else {
                    toast("erro no google client")
                }
            }
        })
        recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

}