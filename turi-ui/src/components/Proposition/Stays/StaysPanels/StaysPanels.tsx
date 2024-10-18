import { useTranslation } from "react-i18next";
import ButtonPanel from "../../../Panel";
import styles from './StaysPanels.module.css'

const StaysPanels = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.panels}>
            <ButtonPanel
                text={t('home.proposition.hotels')}
                imagePath={'src/assets/images/proposition/stay/hotel.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.houses')}
                imagePath={'src/assets/images/proposition/stay/house.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.apartments')}
                imagePath={'src/assets/images/proposition/stay/apartment.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.guesthouses')}
                imagePath={'src/assets/images/proposition/stay/guesthouse.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.privates')}
                imagePath={'src/assets/images/proposition/stay/private.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.b&b')}
                imagePath={'src/assets/images/proposition/stay/b&b.jpeg'}
            />
        </div>
    )
}

export default StaysPanels;