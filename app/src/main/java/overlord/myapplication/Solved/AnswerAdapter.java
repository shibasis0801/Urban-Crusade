package overlord.myapplication.Solved;

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
 * Created by OverlordPC on 26-Jan-16.
 */
public class AnswerAdapter extends ArrayAdapter<Answer> {
    ArrayList<Answer> answerArrayList;
    Activity context;
    static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
    public AnswerAdapter(Activity context, ArrayList<Answer> answers){
        super(context, R.layout.row,answers);
        this.answerArrayList = answers;
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
        String question = answerArrayList.get(position).toString();
        viewHolder.textView.setText(question);
        return rowView;
    }
}
