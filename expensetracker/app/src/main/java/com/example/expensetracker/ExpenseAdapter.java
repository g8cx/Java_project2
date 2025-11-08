package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<Expense> expenseList;
    private DatabaseHelper dbHelper;

    public ExpenseAdapter(List<Expense> expenseList, DatabaseHelper dbHelper) {
        this.expenseList = expenseList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Используем наш новый макет вместо стандартного
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        // Обрезаем длинное описание
        String description = expense.getDescription();
        if (description.length() > 25) {
            description = description.substring(0, 25) + "...";
        }

        holder.tvDescription.setText(description);
        holder.tvDate.setText(expense.getDate());
        holder.tvAmount.setText(expense.getAmount() + " руб.");

        // Обработчик нажатия на кнопку удаления
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаляем из базы данных
                dbHelper.deleteExpense(expense.getId());
                // Удаляем из списка
                expenseList.remove(position);
                // Обновляем адаптер
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, expenseList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvDate, tvAmount;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}