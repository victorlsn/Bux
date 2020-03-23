package com.victorlsn.bux.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.victorlsn.bux.R
import com.victorlsn.bux.contracts.ProductSubscriptionContract
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.data.api.websocket.WebSocketMessageHandler
import com.victorlsn.bux.listeners.MessageListener
import com.victorlsn.bux.listeners.ProductSubscriptionListener
import com.victorlsn.bux.presenters.ProductSubscriptionPresenter
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment(), ProductSubscriptionContract.View, MessageListener, ProductSubscriptionListener {
    @Inject
    lateinit var messageHandler: WebSocketMessageHandler

    @Inject
    lateinit var presenter: ProductSubscriptionPresenter

    var product: Product? = null

    var subscribed = false

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.attachListener(this)
    }

    override fun onDestroy() {
        messageHandler.removeListener()
        presenter.detachView()
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getSerializable(PRODUCT)?.let {
            product = it as Product
            presenter.subscribe(it.securityId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSubscriptionButton()
        setupProductDetails()
        messageHandler.setListener(this)
    }

    private fun setupSubscriptionButton() {
        subscriptionButton.setOnClickListener {
            when (subscribed) {
                true -> {
                    presenter.unsubscribe(product!!.securityId)
                }
                false -> {
                    presenter.subscribe(product!!.securityId)
                }
            }
        }
    }

    private fun setupProductDetails() {
        product?.let {
            productNameTextView.text = it.displayName
            productCurrentPrice.text = it.currentPrice.getFormattedPrice()
            productPriceDifference.text = it.getFormattedPriceDiff()
            productClosingPrice.text = it.closingPrice.getFormattedPrice()
            productDayRange.text = it.dayRange.getFormattedRange()
            productYearRange.text = it.yearRange.getFormattedRange()
        }
    }

    override fun onError() {
        val errorMessage = getString(R.string.check_connection)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onMessageReceived(message: WebSocketMessage) {
        if (message.body?.securityId == product?.securityId) {
            updateProduct(message.body?.currentPrice)
            onProductSubscriptionChanged(product!!.securityId, true)
        }
    }

    private fun updateProduct(currentPrice: String?) {
        currentPrice?.let {
            product?.currentPrice?.amount = it
            setupProductDetails()
        }
    }

    override fun onProductSubscriptionChanged(securityId: String, subscribed: Boolean) {
        if (this.subscribed != subscribed) {
            this.subscribed = subscribed
            updateSubscriptionButton()
        }
    }

    private fun updateSubscriptionButton() {
        when (subscribed) {
            true -> {
                subscriptionButton.text = getString(R.string.unsubscribe)
            }
            false -> {
                subscriptionButton.text = getString(R.string.subscribe)
            }
        }
    }

    companion object {
        private const val PRODUCT = "PRODUCT"

        fun newInstance(product: Product): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle()
            args.putSerializable(PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }
}