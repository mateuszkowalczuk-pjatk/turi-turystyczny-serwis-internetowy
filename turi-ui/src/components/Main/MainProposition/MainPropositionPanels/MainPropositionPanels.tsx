import MainPropositionPanel from '../MainPropositionPanel'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import { AttractionType } from '../../../../types/attraction.ts'
import { SearchMode } from '../../../../types/offer.ts'
import styles from './MainPropositionPanels.module.css'

interface Props {
    firstText: string
    firstImage: string
    firstTouristicPlaceType?: TouristicPlaceType
    firstAttractionType?: AttractionType
    secondText: string
    secondImage: string
    secondTouristicPlaceType?: TouristicPlaceType
    secondAttractionType?: AttractionType
    thirdText: string
    thirdImage: string
    thirdTouristicPlaceType?: TouristicPlaceType
    thirdAttractionType?: AttractionType
    fourthText: string
    fourthImage: string
    fourthTouristicPlaceType?: TouristicPlaceType
    fourthAttractionType?: AttractionType
    fifthText: string
    fifthImage: string
    fifthTouristicPlaceType?: TouristicPlaceType
    fifthAttractionType?: AttractionType
    sixthText: string
    sixthImage: string
    sixthTouristicPlaceType?: TouristicPlaceType
    sixthAttractionType?: AttractionType
    mode: SearchMode
}

const MainPropositionPanels = ({
    firstText,
    firstImage,
    firstTouristicPlaceType,
    firstAttractionType,
    secondText,
    secondImage,
    secondTouristicPlaceType,
    secondAttractionType,
    thirdText,
    thirdImage,
    thirdTouristicPlaceType,
    thirdAttractionType,
    fourthText,
    fourthImage,
    fourthTouristicPlaceType,
    fourthAttractionType,
    fifthText,
    fifthImage,
    fifthTouristicPlaceType,
    fifthAttractionType,
    sixthText,
    sixthImage,
    sixthTouristicPlaceType,
    sixthAttractionType,
    mode
}: Props) => {
    return (
        <div className={styles.panels}>
            <MainPropositionPanel
                text={firstText}
                imagePath={firstImage}
                mode={mode}
                touristicPlaceType={firstTouristicPlaceType}
                attractionType={firstAttractionType}
            />
            <MainPropositionPanel
                text={secondText}
                imagePath={secondImage}
                mode={mode}
                touristicPlaceType={secondTouristicPlaceType}
                attractionType={secondAttractionType}
            />
            <MainPropositionPanel
                text={thirdText}
                imagePath={thirdImage}
                mode={mode}
                touristicPlaceType={thirdTouristicPlaceType}
                attractionType={thirdAttractionType}
            />
            <MainPropositionPanel
                text={fourthText}
                imagePath={fourthImage}
                mode={mode}
                touristicPlaceType={fourthTouristicPlaceType}
                attractionType={fourthAttractionType}
            />
            <MainPropositionPanel
                text={fifthText}
                imagePath={fifthImage}
                mode={mode}
                touristicPlaceType={fifthTouristicPlaceType}
                attractionType={fifthAttractionType}
            />
            <MainPropositionPanel
                text={sixthText}
                imagePath={sixthImage}
                mode={mode}
                touristicPlaceType={sixthTouristicPlaceType}
                attractionType={sixthAttractionType}
            />
        </div>
    )
}

export default MainPropositionPanels
