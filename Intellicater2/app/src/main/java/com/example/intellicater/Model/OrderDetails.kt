package com.example.waveoffood.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

class OrderDetails(
    var userUid: String? = null,
    var menuKey: MutableList<String>? = null,
    var foodNames: MutableList<String>? = null,
    var foodPrices: MutableList<String>? = null,
    var foodQuantities: MutableList<Int>? = null,
    var totalPrice: String? = null,
    var orderAccepted: Boolean = false,
    var paymentReceived: Boolean = false,
    var itemPushKey: String? = null,
    var currentTime: Long = 0,
    var tokenNum: String? = null // Add tokenNum property

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createIntArray()?.toMutableList(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString() // Read tokenNum from parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeStringList(menuKey)
        parcel.writeStringList(foodNames)
        parcel.writeStringList(foodPrices)
        parcel.writeIntArray(foodQuantities?.toIntArray())
        parcel.writeString(totalPrice)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
        parcel.writeString(tokenNum) // Write tokenNum to parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}
