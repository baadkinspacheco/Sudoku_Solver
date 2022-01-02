'''     Author: Brooke Adkins
        Description: Sudoku solver
'''

def make_grid(size):
    """ Passes in integer size of grid.
    Returns list of tuple coordinates. """
    grid = []
    for i in range(size):
        row = []
        for j in range(size):
            row.append((i,j))
        grid.append(row)
    return grid

def coord_values(grid):
    """ Grid is a list of tuples.
    Creates dictionary of positions that map to values.
    """
    coord_nums = dict()
    for row in grid:
        for column in row:
            coord_nums[column] = 0
    return coord_nums

def grid_boxes(grid):
    ### Need to figure out a better way to do this
    boxes = dict()
    box1, box2, box3 = set(), set(), set()
    box4, box5, box6 = set(), set(), set()
    box7, box8, box9 = set(), set(), set()

    for row in grid:
        for column in row:
            if column[0] <= 2 and column[1] <= 2:
                box1.add(column)
            if column[0] <= 2 and 2 < column[1] <= 5:
                box2.add(column)
            if column[0] <= 2 and 5 < column[1] <= 8:
                box3.add(column)
            if 2 < column[0] <= 5 and column[1] <= 2:
                box4.add(column)
            if 2 < column[0] <= 5 and 2 < column[1] <= 5:
                box5.add(column)
            if 2 < column[0] <= 5 and 5 < column[1] <= 8:
                box6.add(column)
            if 5 < column[0] <= 8 and column[1] <= 2:
                box7.add(column)
            if 5 < column[0] <= 8 and 2 < column[1] <= 5:
                box8.add(column)
            if 5 < column[0] <= 8 and 5 < column[1] <= 8:
                box9.add(column)
        boxes = {1:box1, 2:box2, 3:box3, 4:box4, 5:box5, 6:box6, 7:box7, 8:box8, 9:box9}

def valid_move(val, coord, coord_nums, boxes):
    """ val: integer
    coord: tuple of int (x,y)
    coord_nums: dictionary of positions and values
    boxes: dictionary of boxes and positions
    Returns true/false if valid move. """
    # Checks that value not in the same box
        TODO

    # Checks horizontal and vertical positions for same value
    for pos in coord_nums:
        if pos[0] == coord[0]:
            if coord_nums[pos] == val:
                return False
        if pos[1] == coord[1]:
            if coord_nums[pos] == val:
                return False

    return True

def add_val(val, coord, coord_nums):
    """ val: integer
    coord: tuple of int (x,y)
    coord_nums: dictionary of positions and values
    Adds value to dictionary if valid move. """
    if valid_move(val, coord, coord_nums) == True:
        coord_nums[coord] = val

def solve_puzzle():
    """ Iterate through each coordinate and find value
    Recursion or loop? Can't decide... maybe try both.
    """
    pass

def dashed_spaces(size):
    """ Passes in an integer; size of the grid
    width and length. Returns formated dashes
    to print in grid. """
    blocks = 3 # blocks in grid: 3x3 coords
    dashes = '-' * (size - 1)
    total_dashes = dashes * blocks
    return f' {total_dashes}'

def print_puzzle(size, grid, coord_nums):
    """ size: integer length/width of grid
    grid: list of tuple coordinates
    coord_nums: dictionary of numbers that map to positions
    Prints out the puzzle. """
    for i in range(9):
        if i % 3 == 0:
            print(f'   {i}', end='')
        else:
            print(f' {i}', end='')
    print()

    print(dashed_spaces(size))

    row_count = 0 # keeps track of row on in grid
    count = 1 # count number of coordinates, restarts every 3
    for row in grid:
        print(f'{row_count}|', end='')

        for column in row:
            print(f' {coord_nums[column]}', end='')

            # separates coordinates every third coord
            if count == 3:
                print(' |', end='')

            # restarts counter for placement of '|'
            if count == 3:
                count = 0

            count += 1

        if row_count in (2,5,8):
            print('\n' + dashed_spaces(size), end='')

        row_count += 1
        print()

def add_value(val, coord, coord_nums):
    """ val: integer value
    coord: tuple of (x,y) coordinates
    coord_nums: dictionary of positions and values
    Adds value to position
    """
    assert 0 < val <= 9
    coord_nums[coord] = val

def main():
    size = 9

    # Make a grid
    grid = make_grid(size)

    # Create dictionary that maps numbers to coordinates
    coord_nums = coord_values(grid)

    # create boxes
    grid_boxes(grid)

    print_puzzle(size, grid, coord_nums)

    while True:
        row = int(input('Enter row: '))
        column = int(input('Enter column: '))
        val = int(input('Enter value between 1 and 9: '))

        add_value(val, (row,column), coord_nums)

        # Prints out the puzzle
        print()
        print_puzzle(size, grid, coord_nums)

        cont = input('Do you want to continue adding values? Answer yes/no.\n')

        if cont.lower() == 'no':
            break

main()

