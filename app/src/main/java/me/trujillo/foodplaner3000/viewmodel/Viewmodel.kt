package me.trujillo.foodplaner3000.viewmodel

import androidx.lifecycle.ViewModel



class qViewModel : ViewModel() {
    var items = listOf(
        items("Eier", "14"),
        items("Mehl", "50g"),
        items("Fleisch", "200g"),
        items("Butter", "150g"),
        items("Brockoli", "1"),
        items("Mandeln", "500g"),
        items("Chilli", "50g"),
        items("Milch", "1l"),
        items("Curry", "20g"),
        items("Salat", "500g")
    )
}

