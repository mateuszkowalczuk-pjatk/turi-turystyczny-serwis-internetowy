import { useTranslation } from "react-i18next";
import Logo from "../../Logo";
import HeaderLinks from "../HeaderLinks";
import TextRegular from "../../Controls/Text/TextRegular";
import HeaderButtons from "../AuthButtons";
import styles from './UserHeader.module.css'

const UserHeader = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.header}>
            <Logo />
            <HeaderLinks
                firstLink={
                    <TextRegular
                        text={t('header.reservation')}
                    />
                }
                secondLink={
                    <TextRegular
                        text={t('header.stays')}
                    />
                }
                thirdLink={
                    <TextRegular
                        text={t('header.favourites')}
                    />
                }
            />
            <HeaderButtons
                firstButton={t('header.premium')}
                secondButton={t('header.name')}
            />
        </div>
    )
}

export default UserHeader;