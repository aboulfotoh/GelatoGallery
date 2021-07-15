package com.gelato.gelatogallery.network

import com.gelato.gelatogallery.data.model.Images
import retrofit2.http.*

interface ApiEndpointInterface {

    @GET("list")
    suspend fun getImages(@Query("page")page :Int,@Query("limit")limit:Int): Images

    /*@POST(ApiUrls.VENDOR_USER_REGISTER)
    suspend  fun registerUser(@Body user: User): LoginResult

    @POST(ApiUrls.LOGIN)
    suspend fun loginAsync(@Body loginRequest: UserLoginRequest): LoginResult

    @POST(ApiUrls.UPDATE_SETTINGS)
    suspend fun updateSettings(@Body settings: VendorSettings): VendorSettings

    @GET(ApiUrls.ORGANIZATION_BRANCHES)
    suspend fun getReservationsBranchesAsync(
        @Path("organizationID") organizationID: Int,
        @Path("userID") userID: Int
    ): VendorOrganizationBranch.VendorOrganizationBranchResult

    @GET(ApiUrls.ORGANIZATION_BRANCHES)
    suspend fun getBranchesAsync(
        @Path("organizationID") organizationID: Int,
        @Path("userID") userID: Int
    ): VendorOrganizationBranch.VendorOrganizationBranchResult

    @GET(ApiUrls.CATEGORIES)
    suspend fun getCategoriesAsync(): VendorCategories

    @GET(ApiUrls.SOCIAL_TYPES)
    suspend fun getSocialLinksAsync(): SocialLinks

    @POST(ApiUrls.RESERVATIONS)
    suspend fun getReservationsAsync(@Body generalRequest: GeneralRequest): Reservations

    @GET(ApiUrls.GET_PACKAGES)
    suspend fun getVendorPackages(
        @Path("userID") userID: Int,
        @Path("organizationID") organizationID: Int
    ): Packages

    @POST(ApiUrls.CREATE_PACKAGE)
    suspend fun createPackageWithPrices(@Body vendorPackage: VendorPackage): Packages

    @POST(ApiUrls.CREATE_PACKAGES)
    suspend fun createPackages(@Body vendorPackage: ArrayList<VendorPackage>): Packages

    @POST(ApiUrls.CREATE_PACKAGES_FIXED_PRICE)
    suspend fun createPackagesFixedPrice(@Body vendorPackage: ArrayList<VendorPackage>): Packages

    @POST(ApiUrls.DELETE_PACKAGE)
    suspend fun deletePackage(@Body vendorPackage: VendorPackage): Boolean

    @POST(ApiUrls.ACTIVATE_PACKAGE)
    suspend fun activatePackage(@Body vendorPackage: VendorPackage): Boolean

    @POST(ApiUrls.DELETE_PACKAGE_PRICE)
    suspend fun deletePackagePrice(@Body vendorPackage: VendorPackage): Boolean

    @GET(ApiUrls.GET_SERVICES)
    suspend fun getVendorServices(
        @Path("userID") userID: Int,
        @Path("organizationID") organizationID: Int
    ): Services

    @POST(ApiUrls.CREATE_SERVICE)
    suspend fun createServiceWithPrices(@Body servicePricesRequest: Service): Services

    @POST(ApiUrls.CREATE_SERVICES)
    suspend fun createServices(@Body servicePricesRequest: ArrayList<Service>): Services

    @POST(ApiUrls.CREATE_SERVICES_FIXED_PRICE)
    suspend fun createServicesFixedPrice(@Body servicePricesRequest: ArrayList<Service>): Services

    @POST(ApiUrls.DELETE_SERVICE)
    suspend fun deleteService(@Body servicePricesRequest: Service): Boolean

    @POST(ApiUrls.ACTIVATE_SERVICE)
    suspend fun activateService(@Body servicePricesRequest: Service): Boolean

    @POST(ApiUrls.DELETE_SERVICE_PRICE)
    suspend fun deleteServicePrice(@Body serviceItem: Service): Boolean

    @POST(ApiUrls.ALBUMS)
    suspend fun getAlbums(@Body album: Album): Albums

    @POST(ApiUrls.DELETE_ALBUM)
    suspend fun deleteAlbum(@Body album: Album): Boolean

    @POST(ApiUrls.DELETE_IMAGE)
    suspend fun deleteImage(@Body media: Media): Boolean

    @POST(ApiUrls.ADD_ALBUM)
    suspend fun createAlbum(@Body album: Album): Album
    @POST(ApiUrls.ADD_IMAGE)
    suspend fun addMedia(@Body media: Media): Media

    @POST(ApiUrls.SERVICES_PACKAGES)
    suspend fun getServicesAndPackages(@Body generalRequest: GeneralRequest): VendorReservationOptions

    @GET(ApiUrls.REVIEWS)
    suspend fun getReviews(
        @Path("userID") userID: Int,
        @Path("organizationID") organizationID: Int
    ): Reviews

    @GET(ApiUrls.COUNTRIES)
    suspend fun getCountries(): Countries

    @GET(ApiUrls.USER_ROLES)
    suspend fun getUserRoles(): UserRoles

    @GET(ApiUrls.USERS)
    suspend fun getOrganizationUsers(
        @Path("organizationID") organizationID: Int,
        @Path("organizationBranchID") branchID: Int
    ): UsersResponse

    @POST(ApiUrls.VENDOR_ORGANIZATION_USER_REGISTER)
    suspend fun registerOrganizationUser(@Body user: User): LoginResult

    @POST(ApiUrls.UPDATE_USER)
    suspend fun updateUser(@Body userRegisterRequest: User): LoginResult

    @POST(ApiUrls.ORGANIZATION_BRANCH_DETAILS)
    suspend fun organizationBranch(@Body organizationBranchRequest: VendorOrganizationBranch): VendorOrganizationBranch.VendorOrganizationBranchResult

    @POST(ApiUrls.UPDATE_ORGANIZATION)
    suspend fun organizationUpdate(@Body organizationRequest: VendorOrganization): VendorOrganization

    @POST(ApiUrls.ACTIVATE_BRANCH)
    suspend fun activateOrganizationBranch(@Body item: VendorOrganizationBranch): Boolean

    @POST(ApiUrls.CREATE_ORGANIZATION_USER)
    suspend fun createOrganizationUser(@Body userRegisterRequest: User): LoginResult

    @POST(ApiUrls.CREATE_RESERVATION)
    suspend fun createReservation(@Body createReservationRequest: Reservation): Reservation

    @POST(ApiUrls.SEARCH)
    suspend fun search(@Body generalRequest: GeneralRequest): Reservations

    @POST(ApiUrls.UPDATE_RESERVATION_STATUS)
    suspend fun updateReservationStatus(@Body reservation: Reservation): Reservation

    @POST(ApiUrls.RESPOND_RESERVATION)
    suspend fun respondReservation(@Body reservation: Reservation): Reservation

    @POST(ApiUrls.ADD_PAYMENT)
    suspend fun addPayment(@Body payment: Payment): Payment

    @POST(ApiUrls.ADD_REFUND)
    suspend fun addRefund(@Body refund: Refund): Refund

    @POST(ApiUrls.BLOCK_DATE)
    suspend fun blockDate(@Body generalRequest: GeneralRequest): List<BlockDatesResult.BlockDates>

    @POST(ApiUrls.GET_BLOCK_DATES)
    suspend fun getBlockDate(@Body generalRequest: GeneralRequest): BlockDatesResult

    @POST(ApiUrls.UPDATE_CATEGORY)
    suspend fun updateCategory(@Body generalRequest: GeneralRequest): Boolean

    @POST(ApiUrls.UPDATE_SOCIAL_LINKS)
    suspend fun updateSocialLinks(@Body socialLinks: UpdateSocialLinks): UpdateSocialLinks

    @POST(ApiUrls.UPDATE_WORKING_DAYS)
    suspend fun updateWorkingDays(@Body updateWorkingDays: UpdateWorkingDays): Boolean

    @POST(ApiUrls.GET_SEASONS)
    suspend fun getSeasons(@Body generalRequest: GeneralRequest): Season

    @POST(ApiUrls.ADD_SEASONS)
    suspend fun addSeasons(@Body seasons: ArrayList<Season>): Season

    @POST(ApiUrls.DELETE_SEASON)
    suspend fun deleteSeason(@Body season: Season): Boolean

    @POST(ApiUrls.GET_USER_INFO)
    suspend fun getUserInfo(@Body user: User): LoginResult*/
}