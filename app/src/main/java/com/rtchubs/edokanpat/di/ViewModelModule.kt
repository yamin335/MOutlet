package com.rtchubs.edokanpat.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rtchubs.edokanpat.ViewModelFactory
import com.rtchubs.edokanpat.ar_location.ARLocationViewModel
import com.rtchubs.edokanpat.nid_scan.NIDScanCameraXViewModel
import com.rtchubs.edokanpat.ui.LoginActivityViewModel
import com.rtchubs.edokanpat.ui.MainActivityViewModel
import com.rtchubs.edokanpat.ui.add_payment_methods.AddBankViewModel
import com.rtchubs.edokanpat.ui.add_payment_methods.AddCardViewModel
import com.rtchubs.edokanpat.ui.add_payment_methods.AddPaymentMethodsViewModel
import com.rtchubs.edokanpat.ui.add_product.AddProductViewModel
import com.rtchubs.edokanpat.ui.add_product.AllProductViewModel
import com.rtchubs.edokanpat.ui.cart.CartViewModel
import com.rtchubs.edokanpat.ui.chapter_list.ChapterListViewModel
import com.rtchubs.edokanpat.ui.customers.AddCustomerViewModel
import com.rtchubs.edokanpat.ui.customers.AllCustomersViewModel
import com.rtchubs.edokanpat.ui.customers.SelectCustomerViewModel
import com.rtchubs.edokanpat.ui.exams.ExamsViewModel
import com.rtchubs.edokanpat.ui.home.*
import com.rtchubs.edokanpat.ui.how_works.HowWorksViewModel
import com.rtchubs.edokanpat.ui.info.InfoViewModel
import com.rtchubs.edokanpat.ui.live_chat.LiveChatViewModel
import com.rtchubs.edokanpat.ui.login.SignInViewModel
import com.rtchubs.edokanpat.ui.tou.TouViewModel
import com.rtchubs.edokanpat.ui.otp.OtpViewModel
import com.rtchubs.edokanpat.ui.pre_on_boarding.PreOnBoardingViewModel
import com.rtchubs.edokanpat.ui.profiles.ProfilesViewModel
import com.rtchubs.edokanpat.ui.registration.RegistrationViewModel
import com.rtchubs.edokanpat.ui.settings.SettingsViewModel
import com.rtchubs.edokanpat.ui.setup.SetupViewModel
import com.rtchubs.edokanpat.ui.setup_complete.SetupCompleteViewModel
import com.rtchubs.edokanpat.ui.splash.SplashViewModel
import com.rtchubs.edokanpat.ui.video_play.LoadWebViewViewModel
import com.rtchubs.edokanpat.ui.video_play.VideoPlayViewModel
import com.rtchubs.edokanpat.ui.login.ViewPagerViewModel
import com.rtchubs.edokanpat.ui.more.MoreViewModel
import com.rtchubs.edokanpat.ui.myAccount.MyAccountViewModel
import com.rtchubs.edokanpat.ui.myDevices.MyDevicesViewModel
import com.rtchubs.edokanpat.ui.offers.OffersViewModel
import com.rtchubs.edokanpat.ui.order.CreateOrderViewModel
import com.rtchubs.edokanpat.ui.order.OrderTrackHistoryViewModel
import com.rtchubs.edokanpat.ui.order.OrderViewModel
import com.rtchubs.edokanpat.ui.otp_signin.OtpSignInViewModel
import com.rtchubs.edokanpat.ui.pin_number.PinNumberViewModel
import com.rtchubs.edokanpat.ui.profile_signin.ProfileSignInViewModel
import com.rtchubs.edokanpat.ui.shops.ShopDetailsContactUsViewModel
import com.rtchubs.edokanpat.ui.shops.ShopDetailsViewModel
import com.rtchubs.edokanpat.ui.terms_and_conditions.TermsViewModel
import com.rtchubs.edokanpat.ui.topup.TopUpAmountViewModel
import com.rtchubs.edokanpat.ui.topup.TopUpBankCardViewModel
import com.rtchubs.edokanpat.ui.topup.TopUpMobileViewModel
import com.rtchubs.edokanpat.ui.topup.TopUpPinViewModel
import com.rtchubs.edokanpat.ui.transactions.TransactionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    // PSB View Model Bind Here
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreBookListViewModel::class)
    abstract fun bindMoreBookListViewModel(viewModel: MoreBookListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun bindProductDetailsViewModel(viewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreShoppingMallViewModel::class)
    abstract fun bindMoreShoppingMallViewModel(viewModel: MoreShoppingMallViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllShopListViewModel::class)
    abstract fun bindAllShopListViewModel(viewModel: AllShopListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopDetailsViewModel::class)
    abstract fun bindShopDetailsViewModel(viewModel: ShopDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopDetailsContactUsViewModel::class)
    abstract fun bindShopDetailsContactUsViewModel(viewModel: ShopDetailsContactUsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    abstract fun bindMoreViewModel(viewModel: MoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetAViewModel::class)
    abstract fun bindSetAViewModel(viewModel: SetAViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetBViewModel::class)
    abstract fun bindSetBViewModel(viewModel: SetBViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetCViewModel::class)
    abstract fun bindSetCViewModel(viewModel: SetCViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    abstract fun bindInfoViewModel(viewModel: InfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExamsViewModel::class)
    abstract fun bindExamsViewModel(viewModel: ExamsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NIDScanCameraXViewModel::class)
    abstract fun bindNIDScanCameraXViewModel(viewModel: NIDScanCameraXViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreOnBoardingViewModel::class)
    abstract fun bindPreOnBoardingViewModel(viewModel: PreOnBoardingViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HowWorksViewModel::class)
    abstract fun bindHowWorksViewModel(viewModel: HowWorksViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtpViewModel::class)
    abstract fun bindOtpViewModel(viewModel: OtpViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TouViewModel::class)
    abstract fun bindTouViewModel(viewModel: TouViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetupViewModel::class)
    abstract fun bindSetupViewModel(viewModel: SetupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetupCompleteViewModel::class)
    abstract fun bindSetupCompleteViewModel(viewModel: SetupCompleteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfilesViewModel::class)
    abstract fun bindSetupProfilesViewModel(viewModel: ProfilesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSetupSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewPagerViewModel::class)
    abstract fun bindSetupViewPagerViewModel(viewModel: ViewPagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChapterListViewModel::class)
    abstract fun bindSetupChapterListViewModel(viewModel: ChapterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayViewModel::class)
    abstract fun bindSetupVideoPlayViewModel(viewModel: VideoPlayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadWebViewViewModel::class)
    abstract fun bindSetupLoadWebViewViewModel(viewModel: LoadWebViewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtpSignInViewModel::class)
    abstract fun bindSetupOtpSignInViewModel(viewModel: OtpSignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinNumberViewModel::class)
    abstract fun bindSetupPinNumberViewModel(viewModel: PinNumberViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileSignInViewModel::class)
    abstract fun bindSetupProfileSignInViewModel(viewModel: ProfileSignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel::class)
    abstract fun bindTermsViewModel(viewModel: TermsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPaymentMethodsViewModel::class)
    abstract fun bindAddPaymentMethodsViewModel(viewModel: AddPaymentMethodsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddBankViewModel::class)
    abstract fun bindAddBankViewModel(viewModel: AddBankViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCardViewModel::class)
    abstract fun bindAddCardViewModel(viewModel: AddCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpMobileViewModel::class)
    abstract fun bindTopUpMobileViewModel(viewModel: TopUpMobileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpAmountViewModel::class)
    abstract fun bindTopUpAmountViewModel(viewModel: TopUpAmountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpPinViewModel::class)
    abstract fun bindTopUpPinViewModel(viewModel: TopUpPinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpBankCardViewModel::class)
    abstract fun bindTopUpBankCardViewModel(viewModel: TopUpBankCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ARLocationViewModel::class)
    abstract fun bindARLocationViewModel(viewModel: ARLocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LiveChatViewModel::class)
    abstract fun bindLiveChatViewModel(viewModel: LiveChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun bindAddProductViewModel(viewModel: AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllProductViewModel::class)
    abstract fun bindAllProductViewModel(viewModel: AllProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderTrackHistoryViewModel::class)
    abstract fun bindOrderTrackHistoryViewModel(viewModel: OrderTrackHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllCustomersViewModel::class)
    abstract fun bindAllCustomersViewModel(viewModel: AllCustomersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCustomerViewModel::class)
    abstract fun bindAddCustomerViewModel(viewModel: AddCustomerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OffersViewModel::class)
    abstract fun bindOffersViewModel(viewModel: OffersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyAccountViewModel::class)
    abstract fun bindMyAccountViewModel(viewModel: MyAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyDevicesViewModel::class)
    abstract fun bindMyDevicesViewModel(viewModel: MyDevicesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    abstract fun bindTransactionsViewModel(viewModel: TransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateOrderViewModel::class)
    abstract fun bindCreateOrderViewModel(viewModel: CreateOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCustomerViewModel::class)
    abstract fun bindSelectCustomerViewModel(viewModel: SelectCustomerViewModel): ViewModel
}