package com.victorlsn.bux.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorlsn.bux.R
import com.victorlsn.bux.contracts.ProductsContract
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.data.api.models.WebSocketMessage
import com.victorlsn.bux.data.api.websocket.WebSocketMessageHandler
import com.victorlsn.bux.listeners.MessageListener
import com.victorlsn.bux.listeners.ProductSelectionListener
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

    override fun resumeFragment() {
        messageHandler.setListener(this)
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
        messageHandler.setListener(this)
    }


    private fun setupRecyclerView() {
        val productsAdapter =
            ProductsAdapter(
                object : ProductSelectionListener {
                    override fun onProductSelected(product: Product) {
//                        presenter.subscribe(product.securityId)
                        val productDetailsFragment = ProductDetailsFragment.newInstance(product)
                        childFragmentManager.beginTransaction()
                            .add(R.id.childContainer, productDetailsFragment, "PRODUCT_DETAIL")
                            .commit()
                    }
                }
            )
        productsAdapter.setHasStableIds(true)

        val layoutManager = GridLayoutManager(context, 2)

        productsRecyclerView.layoutManager = layoutManager
        productsRecyclerView.adapter = productsAdapter

        val spacing = 48
        productsRecyclerView.setPadding(spacing, spacing, spacing, spacing)
        productsRecyclerView.clipToPadding = false
        productsRecyclerView.clipChildren = false
        productsRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

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
        if (!loading.isShowing) {
            loading.show()
        }
    }

    override fun hideLoading() {
        if (loading.isShowing) {
            loading.dismiss()
        }
    }

    override fun onMessageReceived(message: WebSocketMessage) {
        val adapter = productsRecyclerView.adapter as ProductsAdapter
        adapter.updateProduct(message.body?.securityId, message.body?.currentPrice)
    }

    override fun onError() {
        val errorMessage = getString(R.string.check_connection)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }
}