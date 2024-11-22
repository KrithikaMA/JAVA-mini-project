let totalIncome = 0;
let expenses = [];
let totalExpense = 0;

function addExpense() {
    const expenseName = document.getElementById('expenseName').value;
    const expenseAmount = parseFloat(document.getElementById('expenseAmount').value);

    if (expenseName && !isNaN(expenseAmount) && expenseAmount > 0) {
        expenses.push({ name: expenseName, amount: expenseAmount });
        totalExpense += expenseAmount;

        const expenseList = document.getElementById('expenseList');
        const expenseItem = document.createElement('li');
        expenseItem.textContent = `${expenseName}: $${expenseAmount.toFixed(2)}`;
        expenseList.appendChild(expenseItem);

        // Clear input fields
        document.getElementById('expenseName').value = '';
        document.getElementById('expenseAmount').value = '';
    } else {
        alert('Please provide valid expense details.');
    }
}

function calculateSavings() {
    totalIncome = parseFloat(document.getElementById('income').value);
    if (isNaN(totalIncome) || totalIncome <= 0) {
        alert('Please enter a valid income.');
        return;
    }

    const totalSavings = totalIncome - totalExpense;

    // Display results
    document.getElementById('totalIncome').textContent = `Income: $${totalIncome.toFixed(2)}`;
    document.getElementById('totalExpenses').textContent = `Total Expenses: $${totalExpense.toFixed(2)}`;
    document.getElementById('totalSavings').textContent = `Total Savings: $${totalSavings.toFixed(2)}`;
}
