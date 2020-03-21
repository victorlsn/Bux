package com.victorlsn.bux.ui.fragments

import android.os.Bundle
import com.kaopiz.kprogresshud.KProgressHUD
import com.victorlsn.bux.ui.activities.BaseActivity
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    open lateinit var loading: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loading = (activity as BaseActivity).loading
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    open fun onBackPressed(): Boolean {
        if (childFragmentManager.fragments.size > 0) {
            if ((childFragmentManager.fragments.last() as? BaseFragment) != null) {
                if (!(childFragmentManager.fragments.last() as? BaseFragment)!!.onBackPressed()) {
                    childFragmentManager.beginTransaction().remove(childFragmentManager.fragments.last()).commitNow()
                }
            }
            else {
                childFragmentManager.beginTransaction().remove(childFragmentManager.fragments.last()).commitNow()
            }
            return true
        }

        return false
    }

    fun clearChildFragmentManager() {
        for (fragment in childFragmentManager.fragments) {
            childFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    fun clearFragmentManager() {
        for (fragment in fragmentManager!!.fragments) {
            fragmentManager!!.beginTransaction().remove(fragment).commit()
        }
    }
}