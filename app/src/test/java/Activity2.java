import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.OnCompleteListener;
import com.google.firebase.auth.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity2 extends AppCompatActivity {

    private EditText editTextData;
    private Button buttonSubmit;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // If user is not signed in, redirect back to LoginActivity
            startActivity(new Intent(Activity2.this, LoginActivity.class));
            finish();
        }

        editTextData = findViewById(R.id.editTextData);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        databaseReference = FirebaseDatabase.getInstance().getReference("user_data").child(currentUser.getUid());

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editTextData.getText().toString().trim();
                if (!data.isEmpty()) {
                    saveDataToFirebase(data);
                }
            }
        });
    }

    private void saveDataToFirebase(String data) {
        // Get the current user's UID
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Push data to Firebase Database
            String userId = currentUser.getUid();
            DatabaseReference userRef = databaseReference.child(userId);

            userRef.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Data saved successfully
                        // Handle success or show a success message
                    } else {
                        // Data save failed
                        // Handle failure or show an error message
                    }
                }
            });
        }
    }
}
