package com.victorlsn.bux.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.victorlsn.bux.R
import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.api.WebSocketMessageHandler
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.listeners.MessageListener
import com.victorlsn.bux.presenters.ProductsPresenter
import com.victorlsn.bux.ui.adapters.ProductsAdapter
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

class ProductsFragment : BaseFragment(), ProductsContract.View, MessageListener {
    @Inject
    lateinit var messageHandler: WebSocketMessageHandler

    @Inject
    lateinit var presenter: ProductsPresenter

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

        presenter.requestAllProductsDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        val productsAdapter =
            ProductsAdapter(
//                object : PaymentMethodSelectedListener {
//                    override fun onPaymentMethodSelected(moduleCode: String) {
//                        this@TicketOverviewFragment.selectedPaymentMethodCode = moduleCode
//                    }
//                }
            )
        productsAdapter.setHasStableIds(true)

        val layoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        productsRecyclerView.layoutManager = layoutManager
        productsRecyclerView.adapter = productsAdapter
    }

    override fun onProductDetailsSuccess(product: Product) {
        val adapter = productsRecyclerView.adapter as ProductsAdapter
        adapter.addProduct(product)
    }

    override fun onProductDetailsFailure(error: String?) {
        val errorMessage = error ?: getString(R.string.default_error_message)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun onMessageReceived(message: WebSocketMessage) {

    }

    companion object {
        fun newInstance(): ProductsFragment {
            val fragment = ProductsFragment()
            return fragment
        }
    }
}