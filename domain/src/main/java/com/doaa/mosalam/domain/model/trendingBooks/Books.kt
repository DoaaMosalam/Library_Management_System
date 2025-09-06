package com.doaa.mosalam.domain.model.trendingBooks

//data class Books(
//    val id: String?= null,
//    val title: String?=null,
//    val author: String?=null,
//    val coverImageUrl: String?=null,
//    val loanCount: Int?=null,
//    val rating: Float?=null,
//    val reviewCount: Int?=null,
//    val createdAt: String?=null
//
//)

data class Volume(
    val kind: String?,
    val id: String?,
    val etag: String?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
    val saleInfo: SaleInfo?,
    val accessInfo: AccessInfo?,
    val searchInfo: SearchInfo?
)
data class VolumeInfo(
    val title: String?,
    val subtitle: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val industryIdentifiers: List<IndustryIdentifier>?,
    val pageCount: Int?,
    val printType: String?,
    val categories: List<String>?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val maturityRating: String?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?,
    val imageLinks: ImageLinks?
)


data class IndustryIdentifier(
    val type: String?,
    val identifier: String?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)

data class SaleInfo(
    val country: String?,
    val saleability: String?,
    val isEbook: Boolean?,
    val listPrice: Price?,
    val retailPrice: Price?,
    val buyLink: String?
)

data class Price(
    val amount: Double?,
    val currencyCode: String?
)

data class AccessInfo(
    val country: String?,
    val viewability: String?,
    val embeddable: Boolean?,
    val publicDomain: Boolean?,
    val textToSpeechPermission: String?,
    val epub: Format?,
    val pdf: Format?,
    val webReaderLink: String?,
    val accessViewStatus: String?,
    val quoteSharingAllowed: Boolean?
)

data class Format(
    val isAvailable: Boolean?,
    val downloadLink: String?
)

data class SearchInfo(
    val textSnippet: String?
)

