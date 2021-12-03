package ge.mormotsadze.todoapp.todolistcells.checkedlistcell

data class CheckedListCellModel(
    val subNoteId: Int,
    val checked: Boolean,
    val description: String
)