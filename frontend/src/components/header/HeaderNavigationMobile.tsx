import { Sheet, SheetTrigger, SheetContent, SheetHeader, SheetTitle } from "@/components/ui/sheet"
import { NavigationButton } from "./NavigationButton"
import { Link } from 'react-router'

const carParkComponents = [
    { title: "Car Rental in Kyiv", href: "/services/rentals/kyiv" },
    { title: "SUV Rental", href: "/services/rentals/suvs" },
    { title: "Electric Car Rental", href: "/services/rentals/electric" },
    { title: "Car Rental in Lviv", href: "/services/rentals/lviv" },
    { title: "Minivan Rental", href: "/services/rentals/minivans" },
    { title: "Luxury Car Rental", href: "/services/rentals/luxury" },
];

const additionalServiceComponents = [
    { title: "Long-term Car Rental", href: "/services/long-term-rental" },
    { title: "Car Rental with Driver", href: "/services/rentals/with-driver" },
];

const onlyTitleButtonsText = ["Car Leasing", "For Business", "Rental Terms", "Contacts", "About Us", "Reviews", "Blog"];

export const HeaderNavigationMobile = () => {
    return (
        <Sheet>
            <SheetTrigger>
                <NavigationButton />
            </SheetTrigger>
            <SheetContent side="left" className="w-64 p-4">
                <SheetHeader>
                    <SheetTitle>Navigation</SheetTitle>
                </SheetHeader>
                <nav className="flex flex-col gap-4 mt-4">
                    {onlyTitleButtonsText.map((text) => (
                        <Link key={text} to="#" className="text-lg font-medium hover:underline">
                            {text}
                        </Link>
                    ))}
                    <div>
                        <h3 className="text-lg font-semibold mt-4">Car Park</h3>
                        <ul className="ml-2 mt-2 space-y-2">
                            {carParkComponents.map(({ title, href }) => (
                                <li key={title}>
                                    <Link to={href} className="text-sm text-gray-700 hover:text-black">
                                        {title}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div>
                        <h3 className="text-lg font-semibold mt-4">Other Services</h3>
                        <ul className="ml-2 mt-2 space-y-2">
                            {additionalServiceComponents.map(({ title, href }) => (
                                <li key={title}>
                                    <Link to={href} className="text-sm text-gray-700 hover:text-black">
                                        {title}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                </nav>
            </SheetContent>
        </Sheet>
    );
};