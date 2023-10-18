package bio.medico.patient.common


/**
Created by Samiran Kumar on 17,September,2023
 **/
fun String.getBngEnglishText(vt: Vt): String {
    return if (this == AppKey.LANGUAGE_EN) vt.textEng else vt.textBng
}

//fun String.getBngEnglishText(specializedDoctorUIType: SpecializedDoctorUIType): String {
//    return if (this == AppKey.LANGUAGE_EN) specializedDoctorUIType.textEng else specializedDoctorUIType.textBng
//}


//language
fun String.numberConvert(nubString: String): String {

    if (this == AppKey.LANGUAGE_EN) {
        return nubString
    }

    var num = ""
    for (element in nubString) {
        num = when (element) {
            '0' -> num + '০'
            '1' -> num + '১'
            '2' -> num + '২'
            '3' -> num + '৩'
            '4' -> num + '৪'
            '5' -> num + '৫'
            '6' -> num + '৬'
            '7' -> num + '৭'
            '8' -> num + '৮'
            '9' -> num + '৯'
            else -> num + element
        }
    }
    if (num == "N/A") {
        num = "নাই"
    }
    return num
}


enum class Vt(val textEng: String, val textBng: String) {
    ScheduleNotAvailable("Schedule not Available!", "এই মুহূর্তে কোনো শিডিউল নেই!"),
    CancelAppointment("Cancel Appointment", "এ্যাপয়েন্টমেন্ট বাতিল করুন"),


    SpecializedDoctor("Specialized Doctor", "বিশেষজ্ঞ ডাক্তার"),
    ConsultationHour("Consultation Hour", "কনসালটেশনের সময়"),
    Back("Back", "পিছনে"),

    MessageBookConfirm(
        "Please confirm if you would like to book an appointment with DOCTOR_NAME on DATE_TIME",
        "DOCTOR_NAME এর সাথে এ্যাপয়েন্টমেন্ট DATE_TIME এ বুক করতে আগ্রহী হলে নিশ্চিত করুন"
    ),
    MessageAlreadyBookedSchedule(
        "You have already booked schedule",
        "আপনি ইতিমধ্যে সময়সূচী বুক করেছেন"
    ),

    MessageSorryYouCannotBookThisAppointment(
        "Sorry, you cannot book this appointment before DATE_TIME.",
        "দুঃখিত, আপনি DATE_TIME এর আগে এই অ্যাপয়েন্টমেন্ট বুক করতে পারবেন না।"
    ),
    MessagePleaseTryAgainAtThatTime(
        "Please Try again at that time.",
        "অনুগ্রহ করে সেই সময়ে আবার চেষ্টা করুন।"
    ),

    ThankYou("Thank you", "আপনাকে ধন্যবাদ"),

    //Dialog text
    AreYouSure("Are you sure?", "আপনি কি নিশ্চিত?"),
    AreYouCancel("Do you want to Cancel?", "আপনি কি বাতিল করতে চান?"),
    Yes("yes", "হ্যাঁ"),
    No("No", "না"),

    OK("ok", "ঠিক আছে"),

    Delete("Delete", "মুছে ফেলুন"),


    Confirm("Confirm", "নিশ্চিত"),
    Cancel("Cancel", "বাতিল"),


    // image_upload_bn("Image Upload","ইমেজ আপলোড"),


    // my_details("My Details", "মাই ডিটেইলস"),

    // patient_name("Patient Name", "রোগীর নাম"),
    //  date_of_birth("Date of Birth", "জন্ম তারিখ"),
    // gender("Gender", "লিঙ্গ"),
    //  weight_kg("Weight(kg)", "ওজন (কেজি)"),
    // height_ft("Height(ft)", "উচ্চতা (ফুট)"),
    // emergency_contact("Emergency Contact", "জরুরী যোগাযোগ"),
    // update("UPDATE", "আপডেট"),


    title_home("Home", "হোম"),

    title_dashboard("Dashboard", ""),
    title_notifications("Notifications", ""),
    coming_soon_txt("Coming soon!", ""),

    hello("Hello,", "হ্যালো,"),
    remaining("Remaining", "বাকি আছে"),
    call_remaining_bn("৯৯ কল", "99 call"),
    recharge_account("Recharge Account", "রিচার্জ অ্যাকাউন্ট"),
    view_prescription("View Prescription", "প্রেসক্রিপশন দেখুন"),
    view_call_history("View Call History", "কল হিস্টরি দেখুন"),

    upload_lab_report_bn("Upload Lab Report", "ল্যাব রিপোর্ট আপলোড করুন"),


    health_package("Buy Health Package", "হেলথ প্যাকেজ কিনুন"),
    subscribe_to_the(
        "Subscribe to the best health plans",
        "সেরা স্বাস্থ্য পরিকল্পনা সাবস্ক্রাইব করুন"
    ),

    buy_medicine("Buy Medicine", "ওষুধ কিনুন"),
    buy_medicine_anytime(
        "Buy medicine anytime from any where",
        "যে কোন জায়গায় যে কোন সময় ঔষধ কিনুন"
    ),

    lab_test("Lab Test", "ল্যাব টেস্ট"),
    book_for_test_amp("<![CDATA[Book for test & checkup]]>", "পরীক্ষা এবং চেকআপের জন্য বুক করুন"),

    call_an_ambulance("Call an Ambulance", "অ্যাম্বুলেন্স কল করুন"),

    call_for_nursing_care("Call for nursing care", "নার্সিং যত্নের জন্য কল করুন"),

    _24_7_service_all_over_the_country(
        "24/7 service all over the country",
        "সারা দেশে 24/7 পরিষেবা"
    ),

    call_for_home_physiotherapy("Home Physiotherapy", "হোম ফিজিওথেরাপির"),


    add_lab_test("Add Lab Test", "ল্যাব পরীক্ষা যোগ করুন"),

    common_health_conditions("Common Health Conditions", "সাধারণ স্বাস্থ্যের অবস্থা"),
    signout("Signout", "সাইন আউট"),
    back("Back", "পিছনে"),
    prescription("Prescription", "প্রেসক্রিপশন"),
    call_history("Call History", "কল হিস্টরি"),
    lab_report("Lab Report", "ল্যাব রিপোর্ট"),
    lab_test_report("Lab Test Report", ""),


    trusted_device_list("Trusted Device List", "ট্রাস্টেড ডিভাইস লিস্ট"),


    prescription_upload("Upload Prescription", "আপলোড প্রেসক্রিপশন"),

    image_upload("Image Upload", "ইমেজ আপলোড"),
    my_details("My Details", "মাই ডিটেইলস"),

    patient_name("Patient Name", "রোগীর নাম"),
    date_of_birth("Date of Birth", "জন্ম তারিখ"),
    age("Age", ""),
    gender("Gender", "লিঙ্গ"),
    weight_kg("Weight(kg)", "ওজন (কেজি)"),
    height_ft("Height(ft)", "উচ্চতা (ফুট)"),
    emergency_contact("Emergency Contact", "জরুরী যোগাযোগ"),

    write_details("Write Details", "বিস্তারিত লিখুন"),
    current_medication("Current Medication", "চলমান ঔষধ"),

    previous_surgery("Previous Surgery", "পূর্ববর্তী সার্জারি"),
    previous_prescription("Previous Prescription", "পূর্ববর্তী প্রেসক্রিপশন"),


    my_health("My Health", "আমার স্বাস্থ্য"),


    valid_till("Valid Till", "ভ্যালিড আপ টু"),
    active_pack("Current Pack", "একটিভ প্যাক"),

    change_pack("Price &amp; Package", "প্যাক"),
    coupons_amp_offer_codes("<![CDATA[Coupons & Offer Codes]]>", "কুপন এবং অফার কোড"),
    purchase_history("Purchase History", "ক্রয় হিস্টরি"),


    sign_in("Enter your phone number", "আপনার ফোন নম্বর লিখুন"),
    sign_in_subTitle(
        "Please confirm your country code and \nenter your mobile number",
        "আপনার দেশের কোড নিশ্চিত করুন এবং \nআপনার মোবাইল নম্বর লিখুন"
    ),

    create_profile_subTitle(
        "Please enter your name and \nenter your date of birth",
        "আপনার নাম লিখুন এবং আপনার জন্ম তারিখ লিখুন"
    ),

    mobile_number("Mobile Number", "মোবাইল নম্বর"),
    password("Password", "পাসওয়ার্ড"),
    forgot("Forgot?", "পাসওয়ার্ড ভুলে গেছেন?"),

    sign_up("Submit", "সাবমিট"),

    sign_up_subTitle("Create Profile", "প্রোফাইল তৈরি করুন"),

    new_to_medico("New to Medico?", "মেডিকোতে নতুন?"),
    already_to_medico("Already On Medico?", "আগে থেকেই অ্যাকাউন্ট আছে?"),
    enter_your_name("Enter your name", "নাম লিখুন"),

    forget_password("Forget Password", "পাসওয়ার্ড ভুলে গেছেন"),
    if_you_forgot_password_nreset_using_registered_mobile_number(
        "If you forgot password \nreset using registered mobile number",
        "নিবন্ধিত মোবাইল নম্বর ব্যবহার করে \nপাসওয়ার্ড রিসেট করুন"
    ),
    send_otp("Send OTP", "ওটিপি পাঠান"),

    otp_verification("OTP Verification", "ওটিপি ভেরিফিকেশন"),

    otp_verification_subtitle("Enter the OTP sent to ", "পাঠানো ওটিপি লিখুন "),

    verify("Verify &amp; Continue", "ভেরিফাই এবং পরবর্তীতে যান"),

    set_new_password("Set new password", "নতুন পাসওয়ার্ড সেট করুন"),
    confirm_password("Confirm Password", "পাসওয়ার্ড নিশ্চিত করুন"),
    change("Change", "পরিবর্তন করুন"),


    yes("Yes", "হ্যাঁ"),
    cancel("Cancel", "বাতিল করুন"),
    are_you_sure("Are you sure?", "তুমি কি নিশ্চিত?"),

    are_you_cancel("Do you want to Cancel?", "আপনি কি বাতিল করতে চান?"),

    are_you_unsub("Do you want to Unsubscribe?", "আপনি কি আনসাবস্ক্রাইব করতে চান?"),

    unsub("Unsubscribe", "আনসাবস্ক্রাইব"),


    delete("Delete", "মুছে ফেলুন"),

    ok("OK", "ওকে"),


    continue_text("Continue", "এগিয়ে যান"),

    update("UPDATE", "আপডেট"),


    chat_now("Chat Now", "চ্যাট করুন"),
    Audio_Call("Audio Call", "অডিও কল"),
    video_call("Video Call", "ভিডিও কল"),

    call_now_en("Call Now", "কল করুন"),

    call_en("Voice Call", "ভয়েস কল"),

    without_internet_en("Using mobile network", "মোবাইল নেটওয়ার্ক ব্যবহার করে"),

    your_next_follow_up("Next Follow up", "পরবর্তী ফলো আপ"),
    pending_medicine("Pending Medicine", "পেন্ডিং মেডিসিন"),
    order_online("Order Online", "অনলাইন অর্ডার"),
    pending_investigation("Pending Investigation", "পেন্ডিং ইনভেস্টিগেশন"),
    delivery_address("Delivery Address", "সরবরাহের ঠিকানা"),
    delivery_type("Delivery Type", "সরবরাহের টাইপ"),

    partners_diagnostic_center("Partners Diagnostic Center", "পার্টনার ডায়াগনস্টিক সেন্টার"),

    remaining_call("Remaining Call", "রিমেনিং কল"),
    payment("Payment", "পেমেন্ট"),


    order_history("Order History", "অর্ডার হিস্ট্রি"),
    order_now_bn("অর্ডার কনফার্ম করুন", ""),
    partners_pharmacy("Partners Pharmacy", "পার্টনার ফার্মেসি"),
    order_details_bn("অর্ডার বিবরণ", ""),
    days("Days", ""),

    order_now("Confirm Order", "অর্ডার কনফার্ম করুন"),
    package_price("Price &amp; Package", "প্যাকেজ এবং মূল্য"),
    buy_now("Buy Now", ""),
    you_are_subscribed_user("You are subscribed user", "আপনি একজন নিবন্ধিত গ্রাহক"),


    close("Close", "বন্ধ"),
    done_bn("সম্পন্ন", "Done"),
    add_more("Add more", "আরো যোগ করুন"),
    write_here_bn("এখানে লিখুন", ""),
    new_item_added("New Item Added", "নতুন আইটেম যোগ হয়েছে"),
    chat("Chat", "চ্যাট"),
    type_a_message("Type a message", "বার্তা টাইপ করুন"),

    add_medicine("Add your prescription here", "প্রেসক্রিপশন যোগ করুন"),
    write_notes("Write Notes", "নোট লিখুন"),


    set_reminder("Set Reminder", "রিমাইন্ডার সেট করুন"),
    add_time("Add Time", "সময় যোগ করুন"),
    medicine_reminder("Medicine Reminder", "মেডিসিন রিমাইন্ডার"),
    discount("Discount Partners", "ডিসকাউন্ট পার্টনার"),

    discount_barcode_scan("Scan your code for eligibility", ""),

    add_reminder("Add Reminder", "রিমাইন্ডার যোগ করুন"),

    life_coverage("Life Coverage", ""),
    task_title("My Notes", ""),
    add_task_title("Add new reminderModel", ""),
    edit_task_title("Edit reminderModel", ""),
    btn_done("Done", ""),
    edit_title("Title", ""),
    edit_desc("Note", ""),
    edit_pwd("Password", ""),
    toolbar_pwd("Enter Password", ""),
    encrypt("Lock reminderModel", ""),
    item_note("View reminderModel", ""),


    empty_notes(
        "Once you add some reminder  \nyou will find it here",
        "আপনার বাছাইকৃত রিমাইন্ডার যোগ করলে, এখানে খুঁজে পাবেন।"
    ),

    pwd_title("Enter password", ""),
    error_pwd("Incorrect password", ""),
    today_s_medicine("Today\'s Medicine", "আজকের ঔষধ"),
    medicine_name("Medicine Name", "ওষুধের নাম"),
    medicine("Medicine", "ঔষধ"),
    dosage("Dosage", "ডোজ"),
    instruction("Instruction", "নির্দেশাবলী"),
    start_date("Start Date", "শুরুর তারিখ"),


    number_of_days_bn("Number of Days", "দিনের সংখ্যা"),


    payment_method("Payment Method", "মূল্যপরিশোধ পদ্ধতি"),

    create_free_account("Create Free Account", "ফ্রি রেজিস্ট্রেশন করুন"),
    or("OR", "অথবা"),


    //<!--SpecializedDoctor-->

    bannerTitle("Schedule YOur Appointment With", "বিশেষজ্ঞ ডাক্তারের"),

    bannerSubTitle("Our Expert Doctor", "এ্যাপয়েন্টমেন্ট পেতে এখানে"),

    bookNow("Book Now", "বুক করুন"),


    order_again("Order Again", "আবার অর্ডার করুন"),
    search_medicine("Search Medicine", "ওষুধ অনুসন্ধান করুন"),
    house_apartment_flat("House/Apartment/Flat*", "বাড়ি/অ্যাপার্টমেন্ট/ফ্ল্যাট*"),
    road("Road*", "রাস্তা*"),
    area("Area*", "এলাকা*"),
    cash_on_delivery("Cash on delivery", "নগদ মূল্যে পরিশোধ"),
    delivery_charge("Delivery Charge :", "ডেলিভারি চার্জ :"),
    sub_total_including_vat("Sub - Total :", "মোট - :"),
    total("Total", "সর্বমোট"),


    please_add_medicine_from_the("Please add medicine from the", "অনুগ্রহ করে ওষুধ যোগ করুন"),
    search_bar("search bar", "সার্চ অপশন থেকে"),

    purchases_medicine_by_List("Purchase Medicine by List", "তালিকা অনুযায়ী ঔষধ ক্রয়"),
    purchases_medicine_by_prescription(
        "Purchase Medicine by Prescription",
        "প্রেসক্রিপশন দ্বারা ঔষধ ক্রয়"
    ),

    purchases_medicine_by_medico_prescription(
        "Purchase Medicine by Medico Prescription",
        "মেডিকো প্রেসক্রিপশন দ্বারা ঔষধ ক্রয়"
    ),

    headerTextEn("Are you sure?", "আপনি কি নিশ্চিত?"),
    messageTextEn("Do you want to Logout?", "আপনি লগআউট করতে চান?"),
    positiveTextEn("Logout", "লগআউট"),
    negativeTextEn("No", "না"),

    network_txt("No Connection", ""),
    no_data_found("No data found", "কোন তথ্য পাওয়া যায়নি"),
    UpdateQuantity("Update Quantity", "আপডেট পরিমাণ"),
    Remove("Remove", "মুছুন"),


    DiscountPartner("Discount Partner", "ডিসকাউন্ট পার্টনার"),
    ScanEligibility("Scan your code for eligibility", "আপনার কোড স্ক্যান করুন"),
    Search("Search","অনুসন্ধান করুন"),
    Hospital("Hospital", "হাসপাতাল"),
    Pharmacy("Pharmacy", "ফার্মেসি"),
    Lab("Lab", "ল্যাব"),
    Go("Go", "সার্চ"),
    DiscountDetails("Discount Details", "ডিসকাউন্ট বিবরণ"),
    GetDiscount(" Get Discount" ,"ডিসকাউন্ট নিন"),
    Congratulations("Congratulations","অভিনন্দন"),

    Address("Address","ঠিকানা"), Discount("Discount","ছাড়"),

    ScanForDiscount("Scan for Discount","ডিসকাউন্টের জন্য স্ক্যান করুন"),
    PlaceQR("Place the QR code inside the area","এলাকার ভিতরে QR কোড রাখুন"),
    ScanningWillStartAutomatically("Scanning will start automatically","স্ক্যানিং স্বয়ংক্রিয়ভাবে শুরু হবে"),

   // Ok = ঠিক আছে
    /*

       health_package( "Buy Health Package","হেলথ প্যাকেজ কিনুন"),
         subscribe_to_the ( "Subscribe to the best health plans","সেরা স্বাস্থ্য পরিকল্পনা সাবস্ক্রাইব করুন"),

         buy_medicine ( "Buy Medicine", "ওষুধ কিনুন"),
         buy_medicine_anytime ( "Buy medicine anytime from any where", "যে কোন জায়গায় যে কোন সময় ঔষধ কিনুন"),

         lab_test ( "Lab Test", "ল্যাব টেস্ট"),
         book_for_test_amp ( "Book for test & checkup", "পরীক্ষা এবং চেকআপের জন্য বুক করুন"),

         call_an_ambulance ( "Call an Ambulance","অ্যাম্বুলেন্স কল করুন"),

         call_for_nursing_care ( "Call for nursing care", "নার্সিং যত্নের জন্য কল করুন"),

         _24_7_service_all_over_the_country ( "24/7 service all over the country", "সারা দেশে ২৪/৭ পরিষেবা"),

         call_for_home_physiotherapy ( "Home Physiotherapy","হোম ফিজিওথেরাপি"),





         Today ( "Today", "আজ"),
         Days ( "Days" , "দিন" ),

    */
    call_for_ambulance_service("Ambulance Service", "অ্যাম্বুলেন্স সার্ভিস"),
    call_for_registered_nurse("Registered Nurse", "নিবন্ধিত সেবিকা"),


    TEXT_PRIVACY(
    "<p>I have read and agree to the <span style=\"color: #017BE6;\"><a href=\"https://medico.bio/terms-conditions\">Terms Conditions</a>, <a href=\"https://medico.bio/privacyPolicy\">Privacy Policy</a></span> and <span style=\"color: #017BE6;\"> <a href=\"https://medico.bio/privacyPolicy\">Return/refund-policy</a></span></p>",
    "<p>আমি পড়েছি এবং এর সাথে একমত <span style=\"color: #008080;\"><a href=\"https://medico.bio/terms-conditions\">শর্তাবলী</a>, <a href=\"https://medico.bio/privacyPolicy\">গোপনীয়তা নীতি </a></span> এবং <span style=\"color: #008080;\"><a href=\"https://medico.bio/privacyPolicy\">রিটার্ন/ফেরত নীতি</a></span></p>"
    )


    //hold option + right/left arrow  curser move word
    //hold option + shift  up/down arrow code move up and down

    // autocompleate suggetion write some later then click TAB

    //control + G  >>> click multiple time next

}

