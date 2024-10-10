import { useTranslation } from "react-i18next";
import ButtonPanel from "../../../Panel";
import styles from './StaysPanels.module.css'

const StaysPanels = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.panels}>
            <ButtonPanel
                text={t('proposition.hotels')}
                imagePath={'src/assets/images/proposition/stay/hotel.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.houses')}
                imagePath={'src/assets/images/proposition/stay/house.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.apartments')}
                imagePath={'src/assets/images/proposition/stay/apartment.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.guesthouses')}
                imagePath={'src/assets/images/proposition/stay/guesthouse.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.privates')}
                imagePath={'src/assets/images/proposition/stay/private.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.b&b')}
                imagePath={'src/assets/images/proposition/stay/b&b.jpeg'}
            />
        </div>
    )
}

export default StaysPanels;