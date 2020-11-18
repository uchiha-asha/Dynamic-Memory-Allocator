import sys


def compare(path1, path2):
	same, total = 0, 0
	with open(path1, 'r') as file1, open(path2, 'r') as file2:
		for line1, line2 in zip(file1, file2):
			total += 1
			if line1 == line2:
				same += 1
				pass
	return (same, total)



if len(sys.argv) < 3:
	print("2 files is necessary")
else:
	print(compare(sys.argv[1], sys.argv[2]))

