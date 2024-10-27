import matplotlib.pyplot as plt
import pandas as pd

# File containing benchmark timestamps
timestamp_file = "benchmark_timestamps.txt"

# Read the timestamps into a DataFrame
def read_timestamps(file_path):
    timestamps = []
    with open(file_path, "r") as file:
        lines = file.readlines()[1:]  # Skip the header line
        for line in lines:
            message, timestamp = line.strip().split(", ")
            timestamps.append((message, float(timestamp)))
    
    return pd.DataFrame(timestamps, columns=["Message", "Timestamp"])

# Analyze and plot timestamps
def analyze_timestamps(df):
    # Separate the operations based on their message prefixes
    operations = ["Socket.IO connection and game created", "Game event triggered", "Inserted game and player", "Modified player", "Deleted player and game"]
    
    # Dictionary to store dataframes for each operation type
    operation_dfs = {}
    
    for op in operations:
        operation_dfs[op] = df[df["Message"].str.contains(op)].copy()
        operation_dfs[op]["Time difference"] = operation_dfs[op]["Timestamp"].diff().fillna(0)

    # Plot the time difference for each operation type
    plt.figure(figsize=(12, 8))
    
    for op, op_df in operation_dfs.items():
        plt.plot(op_df.index, op_df["Time difference"], label=op)

    plt.title("Time difference between actions during benchmarking")
    plt.xlabel("Action index")
    plt.ylabel("Time taken (ms)")
    plt.legend()
    plt.grid(True)
    plt.savefig("images/time_difference_per_action.png")
    plt.show()
    plt.close()

    # Plot cumulative time taken for each operation
    plt.figure(figsize=(12, 8))
    
    for op, op_df in operation_dfs.items():
        plt.plot(op_df.index, op_df["Timestamp"], label=op)

    plt.title("Cumulative time for different benchmarking actions")
    plt.xlabel("Action index")
    plt.ylabel("Cumulative time (ms)")
    plt.legend()
    plt.grid(True)
    plt.savefig("images/cumulative_time_per_action.png")
    plt.show()
    plt.close()

    # 1. Average time per action type
    avg_times = {op: op_df["Time difference"].mean() for op, op_df in operation_dfs.items()}
    plt.figure(figsize=(10, 6))
    plt.bar(avg_times.keys(), avg_times.values())
    plt.title("Average time per action type")
    plt.ylabel("Average time (ms)")
    plt.xticks(rotation=10)
    plt.grid(True)
    plt.savefig("images/average_time_per_action.png")
    plt.show()
    plt.close()

    # 2. Histogram of time differences (distribution)
    for op, op_df in operation_dfs.items():
        plt.figure(figsize=(10, 6))
        plt.hist(op_df["Time difference"], bins=30, alpha=0.7)
        plt.title(f"Time difference distribution: {op}")
        plt.xlabel("Time difference (ms)")
        plt.ylabel("Frequency")
        plt.grid(True)
        plt.savefig(f"images/{op.replace(' ', '_').lower()}_time_difference_distribution.png")
        plt.show()
        plt.close()

    # 3. Peak time detection (Longest time taken for each action)
    for op, op_df in operation_dfs.items():
        max_time_diff = op_df["Time difference"].max()
        max_time_index = op_df["Time difference"].idxmax()

        plt.figure(figsize=(12, 8))
        plt.plot(op_df.index, op_df["Time difference"], label=op)
        plt.scatter(max_time_index, max_time_diff, color='red', zorder=5, label=f'Peak Time: {max_time_diff:.2f} ms')
        plt.title(f"Time difference with Peak: {op}")
        plt.xlabel("Action index")
        plt.ylabel("Time taken (ms)")
        plt.legend()
        plt.grid(True)
        plt.savefig(f"images/{op.replace(' ', '_').lower()}_peak_time.png")
        plt.show()
        plt.close()


# Run the analysis
def run_analysis():
    df = read_timestamps(timestamp_file)
    analyze_timestamps(df)

if __name__ == "__main__":
    run_analysis()
