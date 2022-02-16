package com.eh.niver.exception.handling

import java.lang.RuntimeException

class GroupNotFoundException(override val message: String?) : RuntimeException(message) {
}