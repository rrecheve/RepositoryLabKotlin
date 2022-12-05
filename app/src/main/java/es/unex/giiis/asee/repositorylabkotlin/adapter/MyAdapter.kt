package es.unex.giiis.asee.repositorylabkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.asee.executorslabkotlin.R
import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo

class MyAdapter(myDataset: List<Repo>, listener: OnListInteractionListener?) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var mDataset: List<Repo>

    interface OnListInteractionListener {
        fun onListInteraction(url: String?)
    }

    var mListener: OnListInteractionListener?

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class MyViewHolder(var mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        // each data item is just a string in this case
        var mTextView: TextView
        var mDateView: TextView
        var mItem: Repo? = null

        init {
            mTextView = mView.findViewById(R.id.textView)
            mDateView = mView.findViewById(R.id.dateView)
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    init {
        mDataset = myDataset
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        // Create new views (invoked by the layout manager)
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_text_view, parent, false)
        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mItem = mDataset[position]
        holder.mTextView.setText(mDataset[position].getName())
        holder.mDateView.setText(mDataset[position].getCreatedAt())
        holder.mView.setOnClickListener {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener!!.onListInteraction(holder.mItem!!.getSvnUrl())
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mDataset.size
    }

    fun swap(dataset: List<Repo>) {
        mDataset = dataset
        notifyDataSetChanged()
    }

    fun clear() {
        mDataset = emptyList()
        notifyDataSetChanged()
    }
}