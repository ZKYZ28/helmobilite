package com.example.projettupreferes.adaptater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.models.Category


/*Quand le recyclerView veut s'afficher la première fois il va demander getItemCournt(nbrItem dans la liste)
* Lorsqu'il va être créé la première fois il va appelé autant de fois onCreateViewHolder qu'il n'y a d'éléments
* Ensuite il va appeler onBindViewHolder autant de fois qu'il y a d'éléments*/