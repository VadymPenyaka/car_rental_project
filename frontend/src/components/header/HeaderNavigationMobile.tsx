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
import { Sheet, SheetTrigger, SheetContent, SheetHeader, SheetTitle, SheetDescription } from "../ui/sheet";
import { NavigationButton } from "./NavigationButton";

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

export const HeaderNavigationMobile = () => {
    return (
        <Sheet>
            <SheetTrigger >
                <NavigationButton />
            </SheetTrigger>
            <SheetContent side={"left"}>
                <SheetHeader>
                    <SheetTitle>
                        Navigation
                    </SheetTitle>
                </SheetHeader>
                <div className="">
                    {onlyTitleButtonsText.map(el => <p key={el}>{el}</p>)}
                </div>
            </SheetContent>
        </Sheet>
    )
}