package com.ageone.alarm.Internal.Utilities

class Enums {

    // MARK: Enum MsgType

    enum class MsgType {
        Driver, Dispatcher
    }

    // MARK: Enum ClientDocumentType

    enum class ClientDocumentType {
        Offer, None
    }

    // MARK: Enum DocumentType

    enum class DocumentType {
        None, News, Offer
    }

    // MARK: Enum WishListType

    enum class WishListType {
        Single, Dropdown
    }

    // MARK: Enum PaymentType

    enum class PaymentType {
        Agreement, Card, Cash
    }

    // MARK: Enum AdressType

    enum class AdressType {
        Bar, Other, Restoraunt, Shop
    }

    // MARK: Enum OrderType

    enum class OrderType {
        Created, OnWay, Arrived, WaitingForClient, Cancelled, Accepted
    }

    // MARK: Enum DriverDocumentType

    enum class DriverDocumentType {
        News
    }

}