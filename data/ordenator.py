import sys

def parse_line(line):
    data = line.split('\t')
    num = int(data[0][2:])
    data[0] = f"tt{num:0>10}"
    return data

def order_file(fname):
    with open(fname, "r") as file:
        headers = file.readline()
        lines = [parse_line(line) for line in file.readlines()]

    lines = sorted(lines, key = lambda line: line[0])

    with open(f"sorted.{fname}", "w") as file:
        file.write(f"{headers}")
        for line in lines:
            file.write("\t".join(line))

fnames = sys.argv[1:]
for fname in fnames:
    order_file(fname)
