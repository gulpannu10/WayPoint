package com.example

import com.example.data.repository.WaypointRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var repository: WaypointRepository

    @Before
    fun setUp() {
        repository = WaypointRepository()
    }

    @Test
    fun testDefaultState() {
        assertFalse(repository.isHindiMode.value)
        assertEquals(0, repository.currentTab.value)
        assertEquals("Priya Sharma", repository.patientProfile.value.name)
        assertEquals("A-42", repository.patientProfile.value.opdToken)
        assertEquals(4, repository.timelineSteps.value.size)
        assertEquals(3, repository.prepItems.value.size)
    }

    @Test
    fun testLanguageToggle() {
        assertFalse(repository.isHindiMode.value)
        repository.toggleLanguage()
        assertTrue(repository.isHindiMode.value)
        repository.toggleLanguage()
        assertFalse(repository.isHindiMode.value)
    }

    @Test
    fun testTabSelection() {
        assertEquals(0, repository.currentTab.value)
        repository.selectTab(2)
        assertEquals(2, repository.currentTab.value)
        repository.selectTab(3)
        assertEquals(3, repository.currentTab.value)
    }

    @Test
    fun testPrepChecklistToggle() {
        val initialCrucial = repository.prepItems.value.first { it.id == "prep_3" }
        assertFalse(initialCrucial.isCompleted)

        repository.togglePrepItem("prep_3")
        val toggled = repository.prepItems.value.first { it.id == "prep_3" }
        assertTrue(toggled.isCompleted)
    }

    @Test
    fun testWalletPassToggle() {
        assertFalse(repository.isWalletPassSaved.value)
        repository.toggleWalletPass()
        assertTrue(repository.isWalletPassSaved.value)
    }

    @Test
    fun testEscortAndSahayakRequests() {
        assertFalse(repository.isEscortDispatched.value)
        repository.requestEscort()
        assertTrue(repository.isEscortDispatched.value)

        assertFalse(repository.isSahayakRequested.value)
        repository.requestSahayak()
        assertTrue(repository.isSahayakRequested.value)
    }

    @Test
    fun testCareAiMessageResponse() {
        val initialCount = repository.chatMessages.value.size
        repository.sendMessage("Explain my ECG results")

        // Should add user message + AI response
        assertEquals(initialCount + 2, repository.chatMessages.value.size)
        val lastResponse = repository.chatMessages.value.last()
        assertFalse(lastResponse.isFromUser)
        assertTrue(lastResponse.text.contains("sinus rhythm", ignoreCase = true))
    }

    @Test
    fun testAddAttendant() {
        repository.addAttendant("Anil Sharma", "+91 99999 11111", "Son / पुत्र")
        assertEquals("Anil Sharma", repository.patientProfile.value.attendantName)
        assertEquals("+91 99999 11111", repository.patientProfile.value.attendantPhone)
    }
}
