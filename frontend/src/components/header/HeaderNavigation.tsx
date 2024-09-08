import * as React from "react"

import { cn } from "@/lib/utils"
import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    NavigationMenuTrigger,
} from "@/components/ui/navigation-menu"

const CarParkComponents: { title: string; href: string; description: string }[] = [
    {
        title: "Оренда авто в Києві",
        href: "/services/rentals/kyiv",
        description:
            "Оренда автомобілів у Києві. Широкий вибір транспортних засобів за доступними цінами.",
    },
    {
        title: "Оренда кросоверів",
        href: "/services/rentals/suvs",
        description:
            "Оренда кросоверів для комфортних подорожей по місту та за його межами.",
    },
    {
        title: "Оренда електромобілів",
        href: "/services/rentals/electric",
        description:
            "Зелена альтернатива - оренда електромобілів для екологічних подорожей.",
    },
    {
        title: "Оренда авто в Львові",
        href: "/services/rentals/lviv",
        description:
            "Оренда автомобілів у Львові. Зручні умови та великий вибір авто на будь-який смак.",
    },
    {
        title: "Оренда мінівенів",
        href: "/services/rentals/minivans",
        description:
            "Оренда просторих мінівенів для поїздок великою компанією або сім'єю.",
    },
    {
        title: "Оренда автомобілів класу люкс",
        href: "/services/rentals/luxury",
        description:
            "Оренда автомобілів преміум-класу для тих, хто цінує комфорт і стиль.",
    },
];

const AdditionalServiceComponents: { title: string; href: string; description: string }[] = [
    {
        title: "Довгострокова оренда авто",
        href: "/services/long-term-rental",
        description:
            "Вигідні умови для довгострокової оренди автомобілів на термін від місяця.",
    },
    {
        title: "Оренда авто з водієм",
        href: "/services/rentals/with-driver",
        description:
            "Оренда автомобіля з водієм для особливих подій, ділових зустрічей або туристичних поїздок.",
    },
];

const onlyTitleButtonsText: string[] = ["Лізинг Авто", "Для Бізнесу", "Умови Оренди", "Контакти", "Про нас", "Відгуки", "Блог"]

export const HeaderNavigation = () => {
    return (
        <NavigationMenu className="w-full mt-10">
            <NavigationMenuList className="w-full">
                <NavigationMenuItem>
                    <ListItem title="Головна" key={"Головна"}></ListItem>
                </NavigationMenuItem>
                <NavigationMenuItem>
                    <NavigationMenuTrigger className="text-l">Автопарк</NavigationMenuTrigger>
                    <NavigationMenuContent>
                        <ul className="grid w-[400px] gap-3 md:w-[500px] md:grid-cols-1 lg:w-[600px] ">
                            {CarParkComponents.map((component) => (
                                <ListItem
                                    key={component.title}
                                    title={component.title}
                                    href={component.href}
                                >
                                    {component.description}
                                </ListItem>
                            ))}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
                <NavigationMenuItem>
                    <NavigationMenuTrigger className="text-l">Інші послуги</NavigationMenuTrigger>
                    <NavigationMenuContent>
                        <ul className="grid w-[400px] gap-3 md:w-[500px] md:grid-cols-1 lg:w-[600px] ">
                            {AdditionalServiceComponents.map((component) => (
                                <ListItem
                                    key={component.title}
                                    title={component.title}
                                    href={component.href}
                                >
                                    {component.description}
                                </ListItem>
                            ))}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
                {onlyTitleButtonsText.map((title:string) =>
                    <NavigationMenuItem key={title}>
                        <ListItem title={title} key={title}></ListItem>
                    </NavigationMenuItem>)
                }
            </NavigationMenuList>
        </NavigationMenu>
    )
}

const ListItem = React.forwardRef<
    React.ElementRef<"a">,
    React.ComponentPropsWithoutRef<"a">
>(({ className, title, children, ...props }, ref) => {
    return (
        <li>
            <NavigationMenuLink asChild>
                <a
                    ref={ref}
                    className={cn(
                        "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
                        className
                    )}
                    {...props}
                >
                    <div className="text-l text-nowrap font-medium leading-none">{title}</div>
                    <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
                        {children}
                    </p>
                </a>
            </NavigationMenuLink>
        </li>
    )
})
ListItem.displayName = "ListItem"