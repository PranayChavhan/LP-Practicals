import tkinter as tk

def on_button_click(value):
    current = entry_var.get()
    entry_var.set(current + str(value))

def clear_entry():
    entry_var.set("")

def calculate():
    try:
        result = eval(entry_var.get())
        entry_var.set(result)
    except Exception as e:
        entry_var.set("Error")

# Create the main window
window = tk.Tk()
window.title("Calculator")

# Entry widget for displaying input and result
entry_var = tk.StringVar()
entry = tk.Entry(window, textvariable=entry_var, font=("Helvetica", 16), justify="right", bd=10)
entry.grid(row=0, column=0, columnspan=4)

# Buttons
buttons = [
    '7', '8', '9', '/',
    '4', '5', '6', '*',
    '1', '2', '3', '-',
    '0', '.', '=', '+'
]

row_val = 1
col_val = 0

for button in buttons:
    tk.Button(window, text=button, padx=20, pady=20, font=("Helvetica", 12), command=lambda b=button: on_button_click(b) if b != '=' else calculate()).grid(row=row_val, column=col_val)
    col_val += 1
    if col_val > 3:
        col_val = 0
        row_val += 1

# Clear button
tk.Button(window, text='C', padx=20, pady=20, font=("Helvetica", 12), command=clear_entry).grid(row=row_val, column=col_val)

# Start the Tkinter event loop
window.mainloop()
