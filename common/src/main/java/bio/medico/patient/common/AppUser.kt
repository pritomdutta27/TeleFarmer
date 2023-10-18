package bio.medico.patient.common

object AppUser {

    const val DEVELOPMENT = "DEVELOPMENT"
    const val PRODUCTION = "PRODUCTION"

    const val LOG_API_CALL_DISABLE = false
    val DEBUG_BUILD = BuildConfig.DEBUG
    //-----------------------------------------------

    @JvmField
    var BUILD_MODE = //DEVELOPMENT
        PRODUCTION


    //-----------------------------------------------
    private const val USER_ID_Rana = "01844476978"
    private const val USER_ID_SAMIRAN = "01847137467"
    private const val USER_ID_SAMIR = "01682662795"
    private const val USER_ID_EMON = "01743597621"

    //check this before release build
    @JvmField
    var DEVELOPMENT_ENABLE = false
    private var USER_CURRENT = USER_ID_SAMIRAN

    //========================================================
    @JvmField
    var USER_ID = ""


    //========================================================
    fun setUser() {
        USER_ID = when (USER_CURRENT) {
            USER_ID_Rana -> {
                USER_ID_Rana
            }

            USER_ID_SAMIRAN -> {
                USER_ID_SAMIRAN
            }

            USER_ID_SAMIR -> {
                USER_ID_SAMIR
            }

            USER_ID_EMON -> {
                USER_ID_EMON
            }

            else -> {
                ""
            }
        }
    }
}