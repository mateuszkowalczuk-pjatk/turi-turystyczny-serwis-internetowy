import { useTranslation } from "react-i18next";
import ButtonPanel from "../../../Panel";
import styles from "./AttractionsPanels.module.css";

const AttractionsPanels = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.panels}>
            <ButtonPanel
                text={t('home.proposition.kayaks')}
                imagePath={'src/assets/images/proposition/attraction/kayak.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.ski-slopes')}
                imagePath={'src/assets/images/proposition/attraction/ski-slope.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.saunas')}
                imagePath={'src/assets/images/proposition/attraction/sauna.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.pools')}
                imagePath={'src/assets/images/proposition/attraction/pool.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.quads')}
                imagePath={'src/assets/images/proposition/attraction/quad.jpeg'}
            />
            <ButtonPanel
                text={t('home.proposition.bicycles')}
                imagePath={'src/assets/images/proposition/attraction/bicycle.jpeg'}
            />
        </div>
    )
}

export default AttractionsPanels;