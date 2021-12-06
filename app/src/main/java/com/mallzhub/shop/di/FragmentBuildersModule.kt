package com.mallzhub.shop.di

import com.mallzhub.shop.ar_location.ARLocationFragment
import com.mallzhub.shop.nid_scan.NIDScanCameraXFragment
import com.mallzhub.shop.ui.add_payment_methods.AddBankFragment
import com.mallzhub.shop.ui.add_payment_methods.AddCardFragment
import com.mallzhub.shop.ui.add_payment_methods.AddPaymentMethodsFragment
import com.mallzhub.shop.ui.add_product.AddProductFragment
import com.mallzhub.shop.ui.add_product.AllProductsFragment
import com.mallzhub.shop.ui.cart.CartFragment
import com.mallzhub.shop.ui.chapter_list.ChapterListFragment
import com.mallzhub.shop.ui.customers.AddCustomerFragment
import com.mallzhub.shop.ui.customers.AllCustomersFragment
import com.mallzhub.shop.ui.customers.SelectCustomerFragment
import com.mallzhub.shop.ui.exams.ExamsFragment
import com.mallzhub.shop.ui.gift_point.GiftPointHistoryDetailsFragment
import com.mallzhub.shop.ui.gift_point.GiftPointHistoryFragment
import com.mallzhub.shop.ui.home.*
import com.mallzhub.shop.ui.how_works.HowWorksFragment
import com.mallzhub.shop.ui.info.InfoFragment
import com.mallzhub.shop.ui.login.SignInFragment
import com.mallzhub.shop.ui.terms_and_conditions.TermsAndConditionsFragment
import com.mallzhub.shop.ui.tou.TouFragment
import com.mallzhub.shop.ui.otp.OtpFragment
import com.mallzhub.shop.ui.pre_on_boarding.PreOnBoardingFragment
import com.mallzhub.shop.ui.profiles.ProfilesFragment
import com.mallzhub.shop.ui.registration.RegistrationFragment
import com.mallzhub.shop.ui.settings.SettingsFragment
import com.mallzhub.shop.ui.setup.SetupFragment
import com.mallzhub.shop.ui.setup_complete.SetupCompleteFragment
import com.mallzhub.shop.ui.splash.SplashFragment
import com.mallzhub.shop.ui.video_play.LoadWebViewFragment
import com.mallzhub.shop.ui.video_play.VideoPlayFragment
import com.mallzhub.shop.ui.login.ViewPagerFragment
import com.mallzhub.shop.ui.more.MoreFragment
import com.mallzhub.shop.ui.myAccount.MyAccountsFragment
import com.mallzhub.shop.ui.myDevices.MyDevicesFragment
import com.mallzhub.shop.ui.offers.CreateOfferFragment
import com.mallzhub.shop.ui.offers.OffersFragment
import com.mallzhub.shop.ui.order.CreateOrderFragment
import com.mallzhub.shop.ui.order.OrderListFragment
import com.mallzhub.shop.ui.order.OrderTrackHistoryFragment
import com.mallzhub.shop.ui.otp_signin.OtpSignInFragment
import com.mallzhub.shop.ui.pin_number.PinNumberFragment
import com.mallzhub.shop.ui.products.SelectProductFragment
import com.mallzhub.shop.ui.profile_signin.ProfileSignInFragment
import com.mallzhub.shop.ui.shops.ShopDetailsContactUsFragment
import com.mallzhub.shop.ui.shops.ShopDetailsFragment
import com.mallzhub.shop.ui.shops.ShopDetailsProductListFragment
import com.mallzhub.shop.ui.topup.TopUpAmountFragment
import com.mallzhub.shop.ui.topup.TopUpBankCardFragment
import com.mallzhub.shop.ui.topup.TopUpMobileFragment
import com.mallzhub.shop.ui.topup.TopUpPinFragment
import com.mallzhub.shop.ui.transactions.TransactionDetailsFragment
import com.mallzhub.shop.ui.transactions.TransactionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeTermsAndConditionsFragment(): TermsAndConditionsFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreBookListFragment(): MoreBookListFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreShoppingMallFragment(): MoreShoppingMallFragment

    @ContributesAndroidInjector
    abstract fun contributeAllShopListFragment(): AllShopListFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsFragment(): ShopDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsProductListFragment(): ShopDetailsProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsContactUsFragment(): ShopDetailsContactUsFragment

    @ContributesAndroidInjector
    abstract fun contributeProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun contributeExamsFragment(): ExamsFragment

    @ContributesAndroidInjector
    abstract fun contributeInfoFragment(): InfoFragment

    @ContributesAndroidInjector
    abstract fun contributeNIDScanCameraXFragment(): NIDScanCameraXFragment

    @ContributesAndroidInjector
    abstract fun contributePreOnBoardingFragment(): PreOnBoardingFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreFragment(): MoreFragment

    @ContributesAndroidInjector
    abstract fun contributeSetAFragment(): SetAFragment

    @ContributesAndroidInjector
    abstract fun contributeSetBFragment(): SetBFragment

    @ContributesAndroidInjector
    abstract fun contributeSetCFragment(): SetCFragment

    @ContributesAndroidInjector
    abstract fun contributeHome2Fragment(): Home2Fragment

    @ContributesAndroidInjector
    abstract fun contributeAddPaymentMethodsFragment(): AddPaymentMethodsFragment


    @ContributesAndroidInjector
    abstract fun contributeHowWorksFragment(): HowWorksFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeOtpFragment(): OtpFragment

    @ContributesAndroidInjector
    abstract fun contributeTouFragment(): TouFragment

    @ContributesAndroidInjector
    abstract fun contributeSetupFragment(): SetupFragment

    @ContributesAndroidInjector
    abstract fun contributeSetupCompleteFragment(): SetupCompleteFragment

    @ContributesAndroidInjector
    abstract fun contributeProfilesFragment(): ProfilesFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeViewPagerFragment(): ViewPagerFragment

    @ContributesAndroidInjector
    abstract fun contributeChapterListFragment(): ChapterListFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoPlayFragment(): VideoPlayFragment

    @ContributesAndroidInjector
    abstract fun contributeLoadWebViewFragment(): LoadWebViewFragment

    @ContributesAndroidInjector
    abstract fun contributeOtpSignInFragment(): OtpSignInFragment

    @ContributesAndroidInjector
    abstract fun contributePinNumberFragment(): PinNumberFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileSignInFragment(): ProfileSignInFragment

    @ContributesAndroidInjector
    abstract fun contributeAddBankFragment(): AddBankFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCardFragment(): AddCardFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpMobileFragment(): TopUpMobileFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpAmountFragment(): TopUpAmountFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpPinFragment(): TopUpPinFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpBankCardFragment(): TopUpBankCardFragment

    @ContributesAndroidInjector
    abstract fun contributeARLocationFragment(): ARLocationFragment

    @ContributesAndroidInjector
    abstract fun contributeAddProductFragment(): AddProductFragment

    @ContributesAndroidInjector
    abstract fun contributeAllProductsFragment(): AllProductsFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderListFragment(): OrderListFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderTrackHistoryFragment(): OrderTrackHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeAllCustomersFragment(): AllCustomersFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCustomerFragment(): AddCustomerFragment

    @ContributesAndroidInjector
    abstract fun contributeOffersFragment(): OffersFragment

    @ContributesAndroidInjector
    abstract fun contributeMyDevicesFragment(): MyDevicesFragment

    @ContributesAndroidInjector
    abstract fun contributeMyAccountsFragment(): MyAccountsFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionsFragment(): TransactionsFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateOrderFragment(): CreateOrderFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectCustomerFragment(): SelectCustomerFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectProductFragment(): SelectProductFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateOfferFragment(): CreateOfferFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionDetailsFragment(): TransactionDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeGiftPointHistoryFragment(): GiftPointHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeGiftPointHistoryDetailsFragment(): GiftPointHistoryDetailsFragment
}