package ir.danialchoopan.cloudnotepad.utails

object EndPoints {
    private const val baseUrl = "http://{url}/api/"
    const val loginUrl = "${baseUrl}auth/login"
    const val registerUrl = "${baseUrl}auth/register"
    const val changePasswordUrl = "${baseUrl}auth/changePassword"
    const val checkTokenUrl = "${baseUrl}checkToken"
    const val logoutUrl = "${baseUrl}auth/logout"
    const val userNotesUrl = "${baseUrl}noteuser"
    const val publicNotesUrl = "${baseUrl}note"
    const val addNoteUrl = "${baseUrl}note"
    const val updateNoteUrl = "${baseUrl}note/"
    const val deleteNoteUrl = "${baseUrl}note/"
    const val changeNoteStatusRequest = "${baseUrl}changenotestatus/"

}