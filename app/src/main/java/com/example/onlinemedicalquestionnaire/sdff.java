/*

        question_body.setText(questions.get(curr_question).text);

                answer_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){

        case R.id.answer0_radio:{
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer0_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 0);
        break;
        }

        case R.id.answer1_radio:{
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 1);
        break;
        }

        case R.id.answer2_radio:{
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 2);
        break;
        }

        case R.id.answer3_radio:{
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 3);
        break;
        }

        case R.id.answer4_radio:{
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 4);
        break;
        }

        case R.id.answer5_radio:{
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answers.set(curr_question, 5);
        break;
        }
        }
        }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        curr_question++;
        curr_quetion_display = curr_question + 1;
        curr_answer = answers.get(curr_question);
        if (curr_quetion_display < questions.size()) {
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        question_title.setText("שאלה " + curr_quetion_display + " מתוך " + size);
        question_body.setText(questions.get(curr_question));

        switch (curr_answer) {

        case -1: {

        answer_group.clearCheck();

        for (int i = 0; i < 6; i++) {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        }
        }

        case 0: {
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer0_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer0_radio.setChecked(true);
        break;
        }

        case 1: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer1_radio.setChecked(true);
        break;
        }

        case 2: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer2_radio.setChecked(true);
        break;
        }

        case 3: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer3_radio.setChecked(true);
        break;
        }

        case 4: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer4_radio.setChecked(true);
        break;
        }

        case 5: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer5_radio.setChecked(true);
        break;
        }
        }
        }
        else {
final AlertDialog dialog = new AlertDialog.Builder(QuestionsActivity.this).create();
final View dialogView = getLayoutInflater().inflate(R.layout.finish_dialog, null);
        TextView finish_tv = dialogView.findViewById(R.id.finish_tv);
        Button send_btn = dialogView.findViewById(R.id.send_btn);
        Button back_btn = dialogView.findViewById(R.id.back_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);


        send_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        dialog.dismiss();
        }
        });

        dialog.show();
        }
        }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        scrollView.fullScroll(ScrollView.FOCUS_UP);

        if(curr_question == 0){
final AlertDialog dialog = new AlertDialog.Builder(QuestionsActivity.this).create();
final View dialogView = getLayoutInflater().inflate(R.layout.exit_questionnaire_dialog, null);
        TextView exit_tv = dialogView.findViewById(R.id.exit_tv);
        TextView exit_tv2 = dialogView.findViewById(R.id.exit_tv2);
        Button finish_btn = dialogView.findViewById(R.id.finish_btn);
        Button continueBtn = dialogView.findViewById(R.id.continue_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);


        finish_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        dialog.dismiss();
        }
        });

        dialog.show();
        }

        else{
        curr_question--;
        curr_quetion_display = curr_question+1;
        question_title.setText("שאלה " + curr_quetion_display + " מתוך " + size);
        question_body.setText(questions.get(curr_question));
        curr_answer = answers.get(curr_question);


        switch (curr_answer){

        case -1: {

        answer_group.clearCheck();
        answer0_radio.setChecked(false);

        for (int i = 0; i < 6; i++) {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        }
        }

        case 0: {
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer0_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer0_radio.setChecked(true);
        break;
        }

        case 1: {

        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer1_radio.setChecked(true);
        break;
        }

        case 2: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer2_radio.setChecked(true);
        break;
        }

        case 3: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer3_radio.setChecked(true);
        break;
        }

        case 4: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer4_radio.setChecked(true);
        break;
        }

        case 5: {
        answer0_radio.setBackgroundResource(R.drawable.question_shape);
        answer1_radio.setBackgroundResource(R.drawable.question_shape);
        answer2_radio.setBackgroundResource(R.drawable.question_shape);
        answer3_radio.setBackgroundResource(R.drawable.question_shape);
        answer4_radio.setBackgroundResource(R.drawable.question_shape);
        answer5_radio.setBackgroundResource(R.drawable.sel_answer_shape);
        answer5_radio.setChecked(true);
        break;
        }
        }
        }

        }
        });
*/


/* 1) next and BACK

    next Saving And Load the Result
    Back Save And Load The Result

    2) Listen To the Correct Type Of Question

    3) Picker


    // Cannot move Next If Not Chosen Yet
    // When Click On next Cheking if not the End Of list If yes Sending  Http Get Request....


    //  if backBtn And Not chosen then  Dont Save

*/