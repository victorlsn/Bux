package com.victorlsn.bux.di.modules

import com.victorlsn.bux.di.scopes.FragmentScoped
import com.victorlsn.bux.ui.fragments.ProductDetailsFragment
import com.victorlsn.bux.ui.fragments.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBindingModule {

	@FragmentScoped
	@ContributesAndroidInjector
	internal abstract fun productsFragment(): ProductsFragment

	@FragmentScoped
	@ContributesAndroidInjector
	internal abstract fun productsDetailsFragment(): ProductDetailsFragment
}
