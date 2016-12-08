package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daipresents.tsundoku.R;

import android.net.Uri;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by daipr on 2016/12/07.
 */
public class BookSearchAdapter
        extends ArrayAdapter<BookSearchResultItem> {

    private LayoutInflater layoutInflater;

    static class ViewHolder {
        ImageView smallThumnail;
        TextView title;
        TextView author;
    }

    public BookSearchAdapter(Context context, List<BookSearchResultItem> bookList) {
        super(context, 0, bookList);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.activity_book_search_result_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.smallThumnail = (ImageView) view.findViewById(R.id.smallThumbnail);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.author = (TextView) view.findViewById(R.id.author);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        BookSearchResultItem item = (BookSearchResultItem) getItem(position);

        //viewHolder.smallThumbnail.setImageURI(new UR(item.getSmallThumbnail()));
        viewHolder.title.setText(item.getTitle());
        viewHolder.author.setText(item.getAuthor());

        return view;
    }

}
