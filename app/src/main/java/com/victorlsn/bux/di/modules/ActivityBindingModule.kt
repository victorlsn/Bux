package com.victorlsn.bux.di.modules

import com.victorlsn.bux.di.scopes.ActivityScoped
import com.victorlsn.bux.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

	@ActivityScoped
	@ContributesAndroidInjector(modules = [FragmentBindingModule::class])
	internal abstract fun mainActivity(): MainActivity

}
