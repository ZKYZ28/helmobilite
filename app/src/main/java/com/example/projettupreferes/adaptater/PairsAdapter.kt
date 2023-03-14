package com.example.projettupreferes.adaptater

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.fragments.SeePairFragment
import com.example.projettupreferes.presenters.SeePairPresenter
import java.util.*

class PairsAdapter(
    private val seePairsPresenter : SeePairPresenter,
    var callBacks: SeePairFragment.ISelectPair) :
    RecyclerView.Adapter<PairsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.pair_item_list, parent, false)
        return ViewHolder(view, callBacks)
    }

    override fun getItemCount(): Int {
        return seePairsPresenter.getItemCount()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            seePairsPresenter.showPairOn(holder, position)
    }


    class ViewHolder(val view: View, callBacks: SeePairFragment.ISelectPair) :
        RecyclerView.ViewHolder(view), View.OnClickListener,
        SeePairPresenter.IPairItemScreen {

        private val callBacks: SeePairFragment.ISelectPair

        private val textChoiceOne: TextView
        private val buttonChoiceOne : Button

        private val textChoiceTwo: TextView
        private val buttonChoiceTwo : Button

        var idPair: UUID? = null
        var currentPosition : Int = 0;

        private lateinit var uriChoiceOne : Uri
        lateinit var uriChoiceTwo : Uri



        init {
            this.callBacks = callBacks
            view.setOnClickListener(this)
            textChoiceOne = view.findViewById(R.id.textChoiceOne)
            buttonChoiceOne = view.findViewById(R.id.buttonChoiceOne)

            textChoiceTwo = view.findViewById(R.id.textChoiceTwo)
            buttonChoiceTwo = view.findViewById(R.id.buttonChoiceTwo)

            buttonChoiceOne.setOnClickListener{
                showPopup(uriChoiceOne)
            }

            buttonChoiceTwo.setOnClickListener{
                showPopup(uriChoiceTwo)
            }

        }

        fun showPopup(imageResourceUri: Uri?) {
            val builder = AlertDialog.Builder(itemView.context)

            val imageView = ImageView(itemView.context)
            imageView.setImageURI(imageResourceUri)
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            builder.setView(imageView)

            builder.setPositiveButton("Fermer") { dialog, _ -> dialog.dismiss() }

            val dialog = builder.create()

            dialog.show()

            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(dialog.window?.attributes)
                val displayMetrics = itemView.resources.displayMetrics
                width = displayMetrics.widthPixels
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            dialog.window?.attributes = layoutParams
        }


        override fun toString(): String {
            return super.toString() + " '" + idPair + "'"
        }

        override fun onClick(view: View) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirmation")
            builder.setMessage("Voulez-vous vraiment supprimer la paire ?")
            builder.setPositiveButton("OK") { dialog, which ->
                callBacks.onSelectedPair(idPair, currentPosition)
            }
            builder.setNegativeButton("Annuler", null)
            val dialog = builder.create()
            dialog.show()
        }

        override fun showPair(idPair : UUID?, position: Int) {
            this.idPair = idPair
            this.currentPosition = position;
            (view.context as Activity).runOnUiThread {
                buttonChoiceOne.isEnabled = false
                buttonChoiceTwo.isEnabled = false
            }
        }

        fun displayChoiceOneText(textChoiceOne : String){
            (view.context as Activity).runOnUiThread {
                this.textChoiceOne.isVisible = true
                this.textChoiceOne.text = textChoiceOne
            }
        }

        fun displayChoiceTwoText(textChoiceTwo : String){
            (view.context as Activity).runOnUiThread {
                this.textChoiceTwo.isVisible = true
                this.textChoiceTwo.text = textChoiceTwo
            }
        }

        fun displayButtonChoiceOne(pathChoiceOne: Uri){
            (view.context as Activity).runOnUiThread {
                buttonChoiceOne.isVisible = true
                buttonChoiceOne.isEnabled = true
                uriChoiceOne = pathChoiceOne
            }
        }

        fun displayButtonChoiceTwo(pathChoiceTwo: Uri){
            (view.context as Activity).runOnUiThread {
                buttonChoiceTwo.isVisible = true
                buttonChoiceTwo.isEnabled = true
                uriChoiceTwo = pathChoiceTwo
            }
        }
    }
}