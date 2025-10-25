import Logo from "../ui/Logo";

import ring_phone from "../../assets/phone_2.svg";
import location from "../../assets/location.svg";
import { HeaderNavigationMobile } from "./HeaderNavigationMobile";

export const HeaderInformationMobile = () => {
    return (
        <div className="flex justify-between h-full items-center p-2">
            <Logo />
            <div className="flex">
                <Logo src={ring_phone} width="24rem" className="p-2" />
                <Logo src={location} width="20rem" className="p-2" />
                <HeaderNavigationMobile />
            </div>
        </div>
    );
};
