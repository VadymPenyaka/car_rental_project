import Logo from "../ui/Logo"

import ring_phone from "../../assets/phone_2.svg"
import location from "../../assets/location.svg"
import { HeaderNavigationMobile } from "./HeaderNavigationMobile"

import { useNavigate } from 'react-router'


export const HeaderInformationMobile = () => {
    const navigate = useNavigate()

    return (
        <div className="flex justify-between h-full items-center p-2">
            <div onClick={() => navigate("/")} className="cursor-pointer">
                <Logo />
            </div>
            <div className="flex">
                <Logo src={ring_phone} width="24rem" className="p-2" />
                <Logo src={location} width="20rem" className="p-2" />
                <HeaderNavigationMobile />
            </div>
        </div>
    );
};
