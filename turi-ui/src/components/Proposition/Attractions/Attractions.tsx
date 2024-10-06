import {useTranslation} from "react-i18next";
import ButtonPanel from "../../Panel";
import styles from './Attractions.module.css';

const Attractions = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.attractions}>
            <ButtonPanel
                text={t('proposition.kayaks')}
                imagePath={'src/assets/images/proposition/attraction/kayak.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.ski-slopes')}
                imagePath={'src/assets/images/proposition/attraction/ski-slope.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.saunas')}
                imagePath={'src/assets/images/proposition/attraction/sauna.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.pools')}
                imagePath={'src/assets/images/proposition/attraction/pool.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.quads')}
                imagePath={'src/assets/images/proposition/attraction/quad.jpeg'}
            />
            <ButtonPanel
                text={t('proposition.bicycles')}
                imagePath={'src/assets/images/proposition/attraction/bicycle.jpeg'}
            />
        </div>
    )
}

export default Attractions;