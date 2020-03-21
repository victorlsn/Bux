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
import com.victorlsn.bux.presenters.ProductSubscriptionPresenter
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment(), ProductSubscriptionContract.View, MessageListener {
    @Inject
    lateinit var messageHandler: WebSocketMessageHandler

    @Inject
    lateinit var presenter: ProductSubscriptionPresenter

    var product: Product? = null

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getSerializable(PRODUCT)?.let {
            product = it as Product
        }

        messageHandler.setListener(this)
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

        setupProductDetails()
    }

    private fun setupProductDetails() {
        product?.let {
            productNameTextView.text = it.displayName
            productCurrentPrice.text = it.currentPrice.getFormattedPrice()
            productPriceDifference.text = it.getFormattedPriceDiff()
            productClosingPrice
        }
    }

    override fun onMessageReceived(message: WebSocketMessage) {
        updateProduct(message.body?.currentPrice)
    }

    override fun onError() {
        val errorMessage = getString(R.string.check_connection)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun updateProduct(currentPrice: String?) {
        currentPrice?.let {
            product?.currentPrice?.amount = it
            setupProductDetails()
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