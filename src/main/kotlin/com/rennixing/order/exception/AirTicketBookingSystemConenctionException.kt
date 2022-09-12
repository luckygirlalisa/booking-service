package com.rennixing.order.exception

class AirTicketBookingSystemConenctionException(override val message: String?) : Exception() {
    constructor() : this("Connection failed with air ticket booking system.")
}

