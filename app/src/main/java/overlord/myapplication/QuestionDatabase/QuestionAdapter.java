package overlord.myapplication.QuestionDatabase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import overlord.myapplication.R;

/**
 * Created by OverlordPC on 24-Jan-16.
 */
public class QuestionAdapter extends ArrayAdapter<Question> {
    ArrayList<Question> questionArrayList;
    Activity context;
    static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
    public QuestionAdapter(Activity context, ArrayList<Question> questions){
        super(context, R.layout.row,questions);
        this.questionArrayList = questions;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View rowView = convertView;
        if(rowView == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.row,null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.textView = (TextView)rowView.findViewById(R.id.row_text);
            viewHolder.imageView= (ImageView) rowView.findViewById(R.id.next_image);

            rowView.setTag(viewHolder);

        }

        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        String question = questionArrayList.get(position).toString();
        viewHolder.textView.setText(question);
        return rowView;
    }

    @Override
    public Question getItem(int position) {
        return questionArrayList.get(position);
    }
}
