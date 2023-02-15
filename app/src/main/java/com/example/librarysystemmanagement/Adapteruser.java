package com.example.librarysystemmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adapteruser extends RecyclerView.Adapter<Adapteruser.HolderUser> {

    private Context context ;
MyDbHelper myDbHelper ;
    public Adapteruser(Context context, ArrayList<Modeluser> userList) {
        this.context = context;
        this.userList = userList;
        myDbHelper = new MyDbHelper(context);
    }

    private ArrayList<Modeluser> userList ;

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_user,parent,false);

        return new HolderUser(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, @SuppressLint("RecyclerView") int position) {

        Modeluser model = userList.get(position);
        final  String id = model.getId();
        final String name = model.getName();
        final String email = model.getEmail();
        final String phone = model.getPhone();
        final String image = model.getImage();
        final String  dateofbirth = model.getDateofbrith();
        final  String token = model.getBooktoken();

        holder.nameus.setText(name);
        holder.booktokenus.setText("Books token : "+ token);

       // myDbHelper.deleteuser(id);


        if(image.equals("null")){
            holder.profileIv.setImageResource(R.drawable.ic_p);
        } else {
            holder.profileIv.setImageURI(Uri.parse(image));
        }
        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoredialog(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+email,
                        ""+phone,
                        ""+image,
                        ""+token,
                        ""+ dateofbirth);


            }
        });

     holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context ,UserDetaillActivity.class);
                intent.putExtra("USER_ID", id );
                context.startActivity(intent);

            }
        });


    }

    private void showMoredialog( final String position, final String id,final String name,final String email,
                                final String phone,final String image,
                                 final String token,final String  dateofbirth ) {
        String[] options = {"EDIT","DELETE"} ;
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0) {
                    Intent intent = new Intent(context ,AdduserActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME",name);
                    intent.putExtra("EMAIL",email);
                    intent.putExtra("PHONE",phone);
                    intent.putExtra("DATEOFBIRTH",dateofbirth);
                    intent.putExtra("IMAGE",image);
                    intent.putExtra("TOKEN",token);
                    intent.putExtra("isEditMode",true);
context.startActivity(intent);

                } else if (which==1) {
                    myDbHelper.deleteuser(id);
                    ((user)context).onResume();
                }

            }

        });
        builder.create().show();


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class HolderUser extends RecyclerView.ViewHolder {
        ImageView profileIv ;
        TextView nameus , booktokenus ;
       ImageButton  morebtn ;
        public HolderUser (View itemView) {
            super(itemView);
            nameus = itemView.findViewById(R.id.nameU);
            profileIv = itemView.findViewById(R.id.picuserr);
            booktokenus = itemView.findViewById(R.id.BookU);
            morebtn = itemView.findViewById(R.id.morebtn);
        }
    }
}
