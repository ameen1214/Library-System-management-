package com.example.librarysystemmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterbook extends RecyclerView.Adapter<Adapterbook.HolderBook> {

    private Context context ;
    MydbHelperBOOK mydbHelperBOOK ;

    public Adapterbook(Context context, ArrayList<Modelbook> bookList) {
        this.context = context;
        this.bookList = bookList;

        mydbHelperBOOK = new MydbHelperBOOK(context);
    }

    private ArrayList<Modelbook> bookList ;

    @NonNull
    @Override
    public Adapterbook.HolderBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_book,parent,false);

        return new Adapterbook.HolderBook(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapterbook.HolderBook holder, @SuppressLint("RecyclerView") int position) {

        Modelbook model = bookList.get(position);
        final  String id = model.getId();
        final  String name = model.getNamebook();
        final  String author = model.getAuthorbook();
        final  String image = model.getImagebook();
        final  String token = model.getTokenby();

        holder.nameus.setText(name);
        holder.tokenby.setText("Token by : "+ token);

//mydbHelperBOOK.deletebook(id);
        if(image.equals("null")){
            holder.profileIv.setImageResource(R.drawable.ic_p);
        } else {
            holder.profileIv.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context ,book_detaill_activity.class);
                intent.putExtra("BOOK_ID", id );
                context.startActivity(intent);

            }
        });

        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoredialogg(
                ""+position,
                        ""+id,
                        ""+name,
                        ""+author,
                        ""+image,
                        ""+token);

            }
        });

    }

    private void showMoredialogg(final String position, final String id,final String name,final String author,
                                 final String image,
                                 final String token) {

        String[] options = {"EDIT BOOK","DELETE BOOK"} ;
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0) {
                    Intent intent = new Intent(context ,AddBookActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME",name);
                    intent.putExtra("AUTHOR",author);
                    intent.putExtra("IMAGE",image);
                    intent.putExtra("TOKEN",token);
                    intent.putExtra("isEditModee",true);
                    context.startActivity(intent);

                } else if (which==1) {
                    mydbHelperBOOK.deletebook(id);
                    ((books)context).onResume();
                }

            }

        });
        builder.create().show();



    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class HolderBook extends RecyclerView.ViewHolder {
        ImageView profileIv ;
        TextView nameus , tokenby ;
        ImageButton morebtn ;
        public HolderBook (View itemView) {
            super(itemView);
            nameus = itemView.findViewById(R.id.BoookU);
            profileIv = itemView.findViewById(R.id.picboook);
            tokenby = itemView.findViewById(R.id.Tokenbyy);
            morebtn = itemView.findViewById(R.id.morebtnBook);
        }
    }
}
