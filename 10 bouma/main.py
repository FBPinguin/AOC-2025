data = open("DATA-10.txt", "r")

indicatorlights = []
buttons = []

for line in data:
    linearray = line.strip().split(" ")
    indicatorlights.append(linearray[0][1:-1])
    buttons.append([])
    for i in range(1, len(linearray) - 1):
        newLst = []
        for e in linearray[i][1:-1].split(","):
            newLst.append(int(e))
        buttons[-1].append(newLst)

def switch(buttonelement, buttonconfig):
    buttonelement = int(buttonelement)
    buttonconfig = list(buttonconfig)
    element = buttonconfig[buttonelement]
    if element == "#":
        buttonconfig[buttonelement] = "."
    elif element == ".":
        buttonconfig[buttonelement] = "#"
    else:
        assert False
    return buttonconfig



def press(button, buttonconfig):
    for i in range(len(button)):
        buttonconfig = switch(button[i], buttonconfig)
    return buttonconfig

def buttonpresses(currentdepth, maxdepth, buttonconfig, buttons, goalbuttonconfig):
    results = []
    for e in buttons:
        if currentdepth == maxdepth:
            if buttonconfig == goalbuttonconfig:
                return True
            else:
                return False
        else:
            results.append(buttonpresses(currentdepth + 1, maxdepth, press(e, buttonconfig), buttons, goalbuttonconfig))
    return any(results)

def size(buttons):
    maxk = 0
    for e in buttons:
        for k in e:
            maxk = max(maxk, k)
    return maxk + 1

def newbuttonpresses(buttons, indicatorlight):
    cache = [[x for x in "...."]]
    moves = [("."*len(indicatorlight), move, 0) for move in buttons]
    
    while (len(moves) > 0):
        (light, move, num) = moves[0]
        moves.remove(moves[0])
        if (light == [x for x in indicatorlight]):
            return num;

        new_light = press(move, light);

        if new_light in cache:
            continue;

        cache.append(new_light)

        for button in buttons:
            moves.append((new_light, button, num+1))


def fewestbuttonpresses(indicatorlights, buttons):
    return newbuttonpresses(buttons, indicatorlights);

def main():
    cnt = 0
    for i in range(len(indicatorlights)):
        print(f"{i + 1} / {len(indicatorlights)}")
        cnt += fewestbuttonpresses(indicatorlights[i], buttons[i])
    print(cnt)

main()