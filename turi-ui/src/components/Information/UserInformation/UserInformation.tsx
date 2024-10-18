import { useTranslation } from "react-i18next";
import InformationPanel from "../InformationPanel";
import InformationContent from "../InformationContent";
import HeaderInformation from "../HeaderInformation";
import InformationReservations from "../InformationReservations";

const UserInformation = () => {
    const { t } = useTranslation();

    return (
        <InformationPanel
            content={
                <InformationContent
                    title={
                        <HeaderInformation
                            text={t('home.information.reservations')}
                        />
                    }
                    content={
                        <InformationReservations />
                    }
                />
            }
        />
    )
}

export default UserInformation;