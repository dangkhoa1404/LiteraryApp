package com.lutech.literaryapp.model

import java.io.File

data class LocalDocument(
    var idDocument: String? = "",
    var nameDocument: String? = "",
    var typeDocument: String? = "",
    var pathDocument: String? = "",
)

data class Account(
    var idNhanVien: String? = "",
    var idChucVu: String? = "",
    var tenNhanVien: String? = "",
    var anhNhanVien: String? = "",
)

data class LocalFile(
    var idFile: String? = "",
    var fileName: String? = "",
    var downloadUrl: String? = ""
)


//Dành cho phần thêm nhân viên mới
data class NewAccEmp(
    var ansSecondLVPass: String? = "",
    var idChucVu: String? = "",
    var idNhanVien: String? = "",
    var matKhau: String? = "",
    var quesSecondLVPass: String? = ""
)

data class NewEmp(
    var anh: String? = "",
    var idNhanVien: String? = "",
    var tenNhanVien: String? = ""
)

