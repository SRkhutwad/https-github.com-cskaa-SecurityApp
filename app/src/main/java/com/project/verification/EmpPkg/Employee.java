package com.project.verification.EmpPkg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.verification.R;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Employee extends AppCompatActivity {

    Button upload;
    TextInputEditText inEdName,inEdAge,inEdSalary;

    CardView adharCard,pan,d_licen,photo,criminal;

    private ImageView photo1,photo2,photo3,photo4,photo5;
    private Bitmap adharimg,panimg,licenimg,ephoto,criminalimg;

    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        photo1 = findViewById(R.id.adharimg);
        photo2 = findViewById(R.id.panimg);
        photo3 = findViewById(R.id.licenimg);
        photo4 = findViewById(R.id.ephoto);
        photo5 = findViewById(R.id.criminalimg);
        upload = findViewById(R.id.upload);

        inEdName = findViewById(R.id.tvName);
        inEdAge = findViewById(R.id.tvAge);
        inEdSalary = findViewById(R.id.tvSalary);

        //CardView
        adharCard = findViewById(R.id.adharCard);
        pan = findViewById(R.id.pan);
        d_licen = findViewById(R.id.d_licen);
        photo = findViewById(R.id.photo);
        criminal = findViewById(R.id.criminal);


        //  String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String salary = getIntent().getStringExtra("salary");

//        Log.d("ID",id);
//        ab.setTitle(name);

        inEdName.setText(name);
        inEdAge.setText(age);
        inEdSalary.setText(salary);


        adharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 0);
            }
        });

        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 1);
            }
        });

        d_licen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 2);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 3);
            }
        });

        criminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 4);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadd();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK)
        {
            adharimg = (Bitmap) data.getExtras().get("data");
            photo1.setImageBitmap(adharimg);
        }

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            panimg = (Bitmap) data.getExtras().get("data");
            photo2.setImageBitmap(panimg);
        }

        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            licenimg = (Bitmap) data.getExtras().get("data");
            photo3.setImageBitmap(licenimg);
        }

        if (requestCode == 3 && resultCode == RESULT_OK)
        {
            ephoto = (Bitmap) data.getExtras().get("data");
            photo4.setImageBitmap(ephoto);
        }

        if (requestCode == 4 && resultCode == RESULT_OK)
        {
            criminalimg = (Bitmap) data.getExtras().get("data");
            photo5.setImageBitmap(criminalimg);
        }

    }

    private void uploadd() {

        final ProgressBar p = findViewById(R.id.progressbar);

        p.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        adharimg.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        final String random = UUID.randomUUID().toString();
        StorageReference imageRef = mStorageRef.child("image/" + random);

        byte[] b = stream.toByteArray();

        imageRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                            }
                        });
                       // Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                       // Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });



        ByteArrayOutputStream streamm = new ByteArrayOutputStream();
        panimg.compress(Bitmap.CompressFormat.JPEG, 100, streamm);

        final String randomm = UUID.randomUUID().toString();
        StorageReference imageReff = mStorageRef.child("image/" + randomm);

        byte[] c = streamm.toByteArray();

        imageReff.putBytes(c)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                            }
                        });
                        Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Photo Uploaded Successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Uploading Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });


        ByteArrayOutputStream streammm = new ByteArrayOutputStream();
        licenimg.compress(Bitmap.CompressFormat.JPEG, 100, streammm);

        final String randommm = UUID.randomUUID().toString();
        StorageReference imageRefff = mStorageRef.child("image/" + randommm);

        byte[] l = streamm.toByteArray();

        imageRefff.putBytes(l)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                            }
                        });
                      //  Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Photo Uploaded Successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                       // Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Uploading Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });



        ByteArrayOutputStream streammmm = new ByteArrayOutputStream();
        ephoto.compress(Bitmap.CompressFormat.JPEG, 100, streammmm);

        final String randommmm = UUID.randomUUID().toString();
        StorageReference imageReffff = mStorageRef.child("image/" + randommmm);

        byte[] e = streamm.toByteArray();

        imageReffff.putBytes(e)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                            }
                        });
                        //  Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Photo Uploaded Successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        // Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Uploading Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });


        ByteArrayOutputStream streammmmm = new ByteArrayOutputStream();
        ephoto.compress(Bitmap.CompressFormat.JPEG, 100, streammmmm);

        final String randommmmm = UUID.randomUUID().toString();
        StorageReference imageRefffff = mStorageRef.child("image/" + randommmmm);

        byte[] r = streamm.toByteArray();

        imageRefffff.putBytes(r)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                            }
                        });
                        //  Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Photo Uploaded Successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        // Toast.makeText(com.project.verification.EmpPkg.Employee.this, "Uploading Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                });





    }

    }
