from PIL import Image, ImageDraw

def get_points():
    points = []
    with open("../../src/main/resources/Year2025/Day09.txt") as file:
        for line in file:
            pointData = line.split(",")
            x = int(pointData[0])
            y = int(pointData[1])
            points.append([x, y])
    return points

def get_min_x(points):
    r = get_max_x(points)
    for p in points:
        r = min(r, p[0])
    return r

def get_max_x(points):
    r = 0
    for p in points:
        r = max(r, p[0])
    return r

def get_min_y(points):
    r = get_max_y(points)
    for p in points:
        r = min(r, p[1])
    return r

def get_max_y(points):
    r = 0
    for p in points:
        r = max(r, p[1])
    return r

def scale(c):
    return c//20

def main():
    points = get_points()
    min_x = get_min_x(points)
    min_y = get_min_y(points)
    max_x = get_max_x(points)
    max_y = get_max_y(points)
    w = scale(max_x) + 1
    h = scale(max_y) + 1
    im = Image.new(mode="RGB", size=(w, h))
    im.paste((10, 10, 10), (0, 0, im.size[0], im.size[1]))
    im_dr = ImageDraw.Draw(im)
    for i, p1 in enumerate(points):
        i2 = (i + 1) % len(points)
        p2 = points[i2]
        shape = [(scale(p1[0]), scale(p1[1])), (scale(p2[0]), scale(p2[1]))]
        im_dr.line(shape, fill="green", width=0)
    im.save("img.png")

if __name__ == "__main__":
    main()